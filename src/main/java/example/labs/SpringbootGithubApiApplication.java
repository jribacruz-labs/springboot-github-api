package example.labs;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootGithubApiApplication implements CommandLineRunner {

    @Value("${GH_TOKEN}")
    private String ghToken;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootGithubApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        GitHub github = new GitHubBuilder().withOAuthToken(ghToken).build();
        GHOrganization organization = github.getOrganization("tre-pa");
        organization.listRepositories().forEach(o -> System.out.println(o.getName().concat(" ").concat(o.getSshUrl())));

    }


}
