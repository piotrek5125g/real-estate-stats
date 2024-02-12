package pl.pni.realestatestats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.pni.realestatestats.constants.SearchOperation;
import pl.pni.realestatestats.repository.HouseRepository;
import pl.pni.realestatestats.repository.RegionRepository;
import pl.pni.realestatestats.service.ExternalApiService;
import pl.pni.realestatestats.service.RealEstateDBUpdateService;
import pl.pni.realestatestats.specification.HouseSearchCriteria;
import pl.pni.realestatestats.specification.HouseSpecification;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class RealEstateStatsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateStatsApplication.class, args);
	}

	@Autowired
	RegionRepository rr;

	@Autowired
	HouseRepository houseRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private RealEstateDBUpdateService realEstateDBUpdateService;

	@Autowired
	private ExternalApiService externalApiService;

	@Value("${external.api.base.url}")
	private String baseUrl;

	@Override
	public void run(String... args) throws Exception {
//		HouseSpecification specification = new HouseSpecification();
//		List<String> typesAsList = List.of("flat","xxx");
//
//		LocalDateTime l1 = LocalDateTime.of(2010,1,1,1,1);
//		LocalDateTime l2 = LocalDateTime.of(2030,1,1,1,1);
//		specification.add(new HouseSearchCriteria("size","M", SearchOperation.EQUAL));
//		specification.add(new HouseSearchCriteria("type",List.of("flat","xxx"), SearchOperation.IN));
//		specification.add(new HouseSearchCriteria("addedDate",l2, SearchOperation.LESS_THAN_EQUAL));
//		specification.add(new HouseSearchCriteria("region", rr.findById("SL_PN").orElseThrow() , SearchOperation.EQUAL));
//
//
//		System.out.println("houses: " + houseRepository.findAll(specification).size());

		//System.out.println("example houses: " + houseRepository.findAll(example).size());

//		WireMockServer wireMockServer = new WireMockServer( 8090);
//		wireMockServer.start();
//
//		wireMockServer.stubFor(WireMock.get(urlPathMatching("/api/real-estates/.*"))
//				.willReturn(aResponse().withBodyFile("realestate.json")));

		//realEstateDBUpdateService.performRealEstateDBUpdateProcedure();
	//	externalApiService.doTest();

//		WebClient client = WebClient.builder()
//				.baseUrl(baseUrl)
//				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				.build();
//
//		String rs = client.get()
//				.uri(uriBuilder -> uriBuilder
//						.path("/api/real-estates/{region}")
//						.queryParam("page", 1)
//						.build("SL_POL"))
//				.retrieve().bodyToMono(String.class).block();
//
//		ObjectMapper mapper = new ObjectMapper();
//		ExternalApiResponse ers = mapper.readValue(rs, ExternalApiResponse.class);
//
//		System.out.println(ers.getTotalPages());



//		String[] sizes= new String[] {"S","M","L"};
//
//		for (int i=0;i<5;i++) {
//			House house = new House();
//			house.setHouseId("test-b-"+i);
//			house.setSize(sizes[i%3]);
//			house.setPrice(100*(i+1));
//			house.setRooms(3);
//			house.setRegion(rr.findById("SL_PN").orElseThrow());
//			house.setType("flat");
//			house.setAddedDate(new Date());
//
//			houseRepository.save(house);
//		}


	}

}
