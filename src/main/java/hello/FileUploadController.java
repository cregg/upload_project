package hello;

import java.io.*;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

@Controller
@EnableGlobalAuthentication
public class FileUploadController {

	@Autowired
	FileRepository fileRepository;

	@Autowired
	SettingRepository settingRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/upload")
	public String provideUploadInfo(Authentication auth,  Model model) {
		model.addAttribute("admin", isAdmin(auth));
		File rootFolder = new File(Application.ROOT);
		List<hello.File> files = fileRepository.findByUserHash(auth.getName().hashCode());
		List<String> userFileNames = Arrays.stream(files.toArray(new hello.File[files.size()]))
			.map(f -> f.getFileName()).collect(Collectors.toList());
		List<String> fileNames = Arrays.stream(rootFolder.listFiles())
			.map(f -> f.getName())
			.collect(Collectors.toList());

		model.addAttribute("files",
			Arrays.stream(rootFolder.listFiles())
					.filter(f -> userFileNames.contains(f.getName()))
					.sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
					.map(f -> f.getName())
					.collect(Collectors.toList())
		);

		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public String handleFileUpload(@RequestParam("name") String name,
								   @RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes,
								   Authentication auth) {
		redirectAttributes.addFlashAttribute("message", isAdmin(auth));
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
			return "redirect:upload";
		}
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:upload";
		}
		Setting setting = settingRepository.getByKey("max_size");
		if (!file.isEmpty()) {
			try {
				File existingFile = new File(Application.ROOT + "/" + name);
				if(existingFile.exists()){
					redirectAttributes.addFlashAttribute("message",
							"File with this name aleady exists. Please rename.");
					return "redirect:upload";
				}
				if(file.getSize() / 1024 >= setting.getValue()){
					redirectAttributes.addFlashAttribute("message",
							"File exceeds max file size. Sorry.");
					return "redirect:upload";
				}
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(Application.ROOT + "/" + name)));
                FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
				hello.File newFile = new hello.File(auth.getName().hashCode(), name);
				fileRepository.save(newFile);
				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded " + name + "!");
			}
			catch (Exception e) {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name + " => " + e.getMessage());
			}
		}
		else {
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload " + name + " because the file was empty");
		}

		return "redirect:upload";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/setting")
	public String changeSettings(@RequestParam("max_size") int kilobites,
								 Authentication auth,  RedirectAttributes ra){
		Setting setting = settingRepository.getByKey("max_size");
		setting.setValue(kilobites);
		settingRepository.save(setting);
		ra.addFlashAttribute("message", "Max File Size Updated. It is now " + kilobites + "kb.");
		return "redirect:upload";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/file")
	public void getFile(@RequestParam("name") String name, HttpServletResponse res)
			throws FileNotFoundException, IOException {
		File existingFile = new File(Application.ROOT + "/" + name);
		if(existingFile.exists()){
			res.addHeader("Content-disposition", "attachment;filename=myfilename.txt");
			res.setContentType("txt/plain");
			// Copy the stream to the response's output stream.
			IOUtils.copy(new FileInputStream(existingFile), res.getOutputStream());
			res.flushBuffer();
		}
	}


	private boolean isAdmin(Authentication auth){
		return Arrays.stream(auth.getAuthorities()
				.toArray(new GrantedAuthority[auth.getAuthorities().size()]))
				.filter(a -> a.getAuthority().equalsIgnoreCase("role_admin"))
				.collect(Collectors.toList()).size() > 0;
	}
}
