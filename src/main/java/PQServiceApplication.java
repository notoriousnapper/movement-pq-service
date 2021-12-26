import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "controllers")


public class PQServiceApplication {

//	private static final Logger log = LoggerFactory.getLogger(PQServiceApplication.class);
//
//	@Autowired
//    SleepSummaryService sleepSummaryService;

	public static void main(String[] args) {
		SpringApplication.run(PQServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// TODO: Interview Problems here



//	@Bean
//	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//		return args -> {
//			Quote quote = restTemplate.getForObject(
//					"https://quoters.apps.pcfone.io/api/random", Quote.class);
//			log.info(quote.toString());
//		};
//	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {

		return args -> {
//			SleepSummary defaultSleepSummary = sleepSummaryService.getDefaultSleepSummary();
//			System.out.println("Default" + defaultSleepSummary);
//			SleepSummary sleepSummary = restTemplate.getForObject(
//					"https://api.ouraring.com/v1/sleep?start=2021-09-01&end=2021-09-04&access_token=3JEBRXC3GYTA6UVHK2KR2LDKAEE3FQOV", SleepSummary.class);
//			log.info("Sleep summary: " + sleepSummary.toString());
//			log.info("Default Sleep summary: ");
		};
	}

}
