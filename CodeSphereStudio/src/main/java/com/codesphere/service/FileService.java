import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {
    private final UserFileRepository fileRepository;
    private final UserRepository userRepository;

    public UserFile createFile(String username, String name, String content, String language) {
        User owner = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserFile file = new UserFile();
        file.setOwner(owner);
        file.setName(name);
        file.setPath("/" + username + "/" + name);
        file.setContent(content);
        file.setLanguage(language);

        return fileRepository.save(file);
    }

    public UserFile updateFile(Long fileId, String content) {
        UserFile file = fileRepository.findById(fileId)
            .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        
        file.setContent(content);
        return fileRepository.save(file);
    }

    public List<UserFile> getUserFiles(String username) {
        return fileRepository.findByOwnerUsername(username);
    }

    public void deleteFile(Long fileId, String username) {
        UserFile file = fileRepository.findById(fileId)
            .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        
        if (!file.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("Not authorized to delete this file");
        }
        
        fileRepository.delete(file);
    }
}
