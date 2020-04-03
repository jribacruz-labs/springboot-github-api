package example.labs;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
public class SpringbootGithubApiApplication implements CommandLineRunner {

    @Value("${GH_TOKEN}")
    private String ghToken;

    @Value("${XTOOL_HOME}")
    private Path xtoolHome;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootGithubApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        GitHub github = new GitHubBuilder().withOAuthToken(ghToken).build();
        this.listOrganizationRepositories(github);
//        this.cloneRepo("https://github.com/tre-pa/springboot-angular2-keycloak-labs.git");


    }

    @SneakyThrows
    private void listOrganizationRepositories(GitHub github) {
        GHOrganization organization = github.getOrganization("tre-pa");
        PagedIterable<GHRepository> ghRepositories = organization.listRepositories();
        for (GHRepository repo : ghRepositories) {
            System.out.println(String.format("Repo: %s, ssh: %s", repo.getName(), repo.getHttpTransportUrl()));
        }
        RepositoryService service = new RepositoryService();
//        service.getOrgRepositories();
    }

    @SneakyThrows
    private void cloneRepo(String sshRepoUrl) {
//        System.out.println(FilenameUtils.getBaseName(sshRepoUrl));
        Git git = Git.cloneRepository()
                .setURI(sshRepoUrl)
                .setDirectory(xtoolHome.resolve("src").resolve(FilenameUtils.getBaseName(sshRepoUrl)).toFile())
                .call();
    }

}
