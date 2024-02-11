package pl.pni.realestatestats;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.pni.realestatestats.repository.HouseRepository;
import pl.pni.realestatestats.repository.RegionRepository;

@SpringBootApplication
@EnableScheduling
public class RealEstateStatsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateStatsApplication.class, args);
	}
	//WireMockServer wireMockServer;

	@Autowired
	RegionRepository rr;

	@Autowired
	HouseRepository houseRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void run(String... args) throws Exception {
		

//		List<String> regionHousesInDb =
//				houseRepository.findHouseIdsByRegion(rr.findById("DLN_WROC_PC").orElseThrow());
//
//		System.out.println("contains " + regionHousesInDb.contains("test-0"));
//		System.out.println("contains2 " + regionHousesInDb.contains("test-1111"));

		//System.out.println("next: " + regionHousesInDb.iterator().next());
//		List<String> types = List.of("flat","detached_house");
//
//		LocalDateTime from = LocalDateTime.of(2010,10,10,0,0);
//		LocalDateTime to = LocalDateTime.of(2045,10,10,0,0);
//
//		System.out.println(
//				houseRepository.findAvgPrice(rr.findById("SL_PN").orElseThrow(), "M",3, types, from , to)
//		);

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

//		System.out.println(rr.findAll());
//		WireMockServer wireMockServer = new WireMockServer( 8090);
//		wireMockServer.start();
//
//		wireMockServer.stubFor(WireMock.get("/api/real-estates/SL_POL?page=1")
//				.willReturn(aResponse().withBodyFile("realestate.json")));
//
//		WebClient client = WebClient.builder()
//				.baseUrl("http://localhost:8090")
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
//		System.out.println(ers.getData().get(0).getType());
	}

}
