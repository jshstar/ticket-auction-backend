// package com.sparta.ticketauction.global.util;
//
// import java.io.File;
// import java.io.IOException;
// import java.io.InputStream;
// import java.net.URL;
// import java.nio.file.Files;
// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;
//
// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
//
// import org.springframework.core.io.Resource;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;
//
// import com.sparta.ticketauction.domain.goods.entity.Goods;
// import com.sparta.ticketauction.domain.goods.entity.GoodsCategory;
// import com.sparta.ticketauction.domain.goods.entity.GoodsInfo;
// import com.sparta.ticketauction.domain.goods.repository.GoodsCategoryRepository;
// import com.sparta.ticketauction.domain.goods.repository.GoodsInfoRepository;
// import com.sparta.ticketauction.domain.goods.repository.GoodsRepository;
// import com.sparta.ticketauction.domain.goods.service.GoodsService;
// import com.sparta.ticketauction.domain.place.entity.Place;
// import com.sparta.ticketauction.domain.place.entity.Zone;
// import com.sparta.ticketauction.domain.place.repository.PlaceRepository;
// import com.sparta.ticketauction.domain.place.repository.ZoneRepository;
// import com.sparta.ticketauction.domain.schedule.service.ScheduleService;
//
// import lombok.RequiredArgsConstructor;
//
// @Controller
// @RequiredArgsConstructor
// public class dataUtil {
//
// 	private final PlaceRepository placeRepository;
// 	private final GoodsInfoRepository goodsInfoRepository;
// 	private final ZoneRepository zoneRepository;
// 	private final GoodsCategoryRepository goodsCategoryRepository;
// 	private final GoodsRepository goodsRepository;
// 	private final GoodsService goodsService;
// 	private final ScheduleService scheduleService;
//
// 	private static String getTagValue(String tag, Element eElement) {
// 		Node nValue = null;
//
// 		NodeList x = eElement.getElementsByTagName(tag);
// 		Node test = x.item(0);
// 		NodeList t = null;
// 		if (test != null) {
// 			t = test.getChildNodes();
// 			if ((Node)t.item(0) != null) {
// 				nValue = (Node)t.item(0);
// 			}
// 		}
// 		if (nValue == null)
// 			return null;
// 		return nValue.getNodeValue();
// 	}
//
// 	public static Integer extractNumbers(String input) {
// 		Pattern pattern = Pattern.compile("\\d+");
// 		Matcher matcher = pattern.matcher(input);
//
// 		StringBuilder result = new StringBuilder();
// 		while (matcher.find()) {
// 			result.append(matcher.group());
// 		}
//
// 		if (result.isEmpty()) {
// 			return null;
// 		}
// 		return Integer.parseInt(result.toString());
// 	}
//
// 	public static List<MultipartFile> convertUrlsToMultipartFiles(List<String> imageUrls) throws IOException {
// 		List<MultipartFile> multipartFiles = new ArrayList<>();
//
// 		for (String imageUrl : imageUrls) {
// 			MultipartFile multipartFile = downloadAndConvertToMultipartFile(imageUrl);
// 			if (multipartFile != null) {
// 				multipartFiles.add(multipartFile);
// 			}
// 		}
//
// 		return multipartFiles;
// 	}
//
// 	private static MultipartFile downloadAndConvertToMultipartFile(String imageUrl) throws IOException {
// 		URL url = new URL(imageUrl);
// 		try (InputStream inputStream = url.openStream()) {
// 			byte[] content = inputStream.readAllBytes();
// 			String fileName = getFileNameFromUrl(imageUrl);
// 			return new MyMultipartFile(content, fileName);
// 		} catch (IOException e) {
// 			// 예외 처리: 이미지를 다운로드하거나 변환하는 도중에 예외 발생 시 null 반환
// 			e.printStackTrace();
// 			return null;
// 		}
// 	}
//
// 	private static String getFileNameFromUrl(String imageUrl) {
// 		// URL에서 마지막 슬래시 이후의 문자열을 파일 이름으로 사용
// 		return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
// 	}
//
// 	public static String extractTextInParentheses(String input) {
// 		Pattern pattern = Pattern.compile("\\((.*?)\\)");
// 		Matcher matcher = pattern.matcher(input);
//
// 		if (matcher.find()) {
// 			return matcher.group(1);
// 		} else {
// 			return input; // 괄호 안의 내용이 없는 경우 빈 문자열 또는 다른 적절한 기본값을 반환할 수 있습니다.
// 		}
// 	}
//
// 	public static LocalTime parseTimeString(String timeString) {
// 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
// 		return LocalTime.parse(timeString, formatter);
// 	}
//
// 	@GetMapping("/api/get/data")
// 	public void fetchDataFromApi(@RequestParam String page, @RequestParam String type,
// 		@RequestParam String status) throws Exception {
// 		String goodsUrl = "http://www.kopis.or.kr/openApi/restful/pblprfr";
// 		String serviceKey = "dc89dcd37d1b46c4b5fb2a880aa144f9";
//
// 		StringBuilder urlBuilder = null;
//
// 		urlBuilder = new StringBuilder(
// 			"http://www.kopis.or.kr/openApi/restful/pblprfr?service=dc89dcd37d1b46c4b5fb2a880aa144f9&cpage=" + page
// 				+ "&rows=10000&prfstate=" + status + "&shcate=" + type + "&signgucode=11");
//
// 		URL url = new URL(urlBuilder.toString());
//
// 		String parsingUrl = "";
// 		parsingUrl = url.toString();
//
// 		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
// 		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
// 		Document doc = dBuilder.parse(parsingUrl);
// 		doc.getDocumentElement().normalize();
// 		NodeList nList = doc.getElementsByTagName("db");
//
// 		for (int i = 0; i < nList.getLength(); i++) {
// 			Node nNode = nList.item(i);
//
// 			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
// 				Element eElement = (Element)nNode;
//
// 				String id = getTagValue("mt20id", eElement);
//
// 				urlBuilder = new StringBuilder(
// 					"http://www.kopis.or.kr/openApi/restful/pblprfr/"
// 						+ id + "?service=dc89dcd37d1b46c4b5fb2a880aa144f9");
//
// 				parsingUrl = new URL(urlBuilder.toString()).toString();
// 				doc = dBuilder.parse(parsingUrl);
// 				doc.getDocumentElement().normalize();
// 				NodeList nList2 = doc.getElementsByTagName("db");
// 				Node goodsDetail = nList2.item(0);
// 				NodeList eElement2 = goodsDetail.getChildNodes();
//
// 				String name = "";
// 				String category = "";
// 				String age = "";
// 				String running_time = "";
// 				String place = "";
// 				String placeId = "";
// 				String start = "";
// 				String end = "";
// 				String time = "";
// 				List<String> images = new ArrayList<>();
//
// 				for (int j = 0; j < eElement2.getLength(); j++) {
// 					if (goodsDetail.getNodeType() == Node.ELEMENT_NODE) {
// 						Node node = eElement2.item(j);
// 						if (node.getNodeName().equals("prfnm")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							name = node.getTextContent();
// 						} else if (node.getNodeName().equals("genrenm")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							category = node.getTextContent();
// 						} else if (node.getNodeName().equals("prfage")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							age = node.getTextContent();
// 						} else if (node.getNodeName().equals("prfruntime")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							running_time = node.getTextContent();
// 						} else if (node.getNodeName().equals("fcltynm")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							place = node.getTextContent();
// 						} else if (node.getNodeName().equals("mt10id")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							placeId = node.getTextContent();
// 						} else if (node.getNodeName().equals("prfpdfrom")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							start = node.getTextContent();
// 						} else if (node.getNodeName().equals("prfpdto")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							end = node.getTextContent();
// 						} else if (node.getNodeName().equals("dtguidance")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							time = node.getTextContent();
// 						} else if (node.getNodeName().equals("poster")) {
// 							if (node.getTextContent().isEmpty() || node.getTextContent().isBlank()) {
// 								break;
// 							}
// 							images.add(node.getTextContent());
// 						} else if (node.getNodeName().equals("styurls")) {
// 							NodeList image = node.getChildNodes();
// 							if (image.getLength() == 0)
// 								break;
// 							for (int k = 0; k < image.getLength(); k++) {
// 								if (image.item(k).getNodeName().equals("styurl")) {
// 									images.add(image.item(k).getTextContent());
// 								}
// 							}
// 						}
// 					}
// 				}
//
// 				Optional<Place> byNamePlace = placeRepository.findByName(place);
//
// 				place = extractTextInParentheses(place);
// 				if (byNamePlace.isEmpty())
// 					byNamePlace = placeRepository.findByName(place);
//
// 				Place getPlace = byNamePlace.orElse(null);
// 				if (byNamePlace.isEmpty()) {
// 					urlBuilder = new StringBuilder(
// 						"http://www.kopis.or.kr/openApi/restful/prfplc/"
// 							+ placeId + "?service=dc89dcd37d1b46c4b5fb2a880aa144f9");
//
// 					parsingUrl = new URL(urlBuilder.toString()).toString();
// 					doc = dBuilder.parse(parsingUrl);
// 					doc.getDocumentElement().normalize();
// 					NodeList placeList = doc.getElementsByTagName("db");
// 					Node placeNode = placeList.item(0);
// 					NodeList placeList2 = placeNode.getChildNodes();
//
// 					String address = "";
// 					String count_seats = "";
// 					String count_p = "";
//
// 					for (int k = 0; k < placeList2.getLength(); k++) {
// 						if (placeNode.getNodeType() == Node.ELEMENT_NODE) {
// 							Node node = placeList2.item(k);
// 							if (node.getNodeName().equals("adres")) {
// 								address = node.getTextContent();
// 							} else if (node.getNodeName().equals("seatscale")) {
// 								count_seats = node.getTextContent();
// 							} else if (node.getNodeName().equals("mt13cnt")) {
// 								count_p = node.getTextContent();
// 							}
// 						}
// 					}
//
// 					Integer c = 1000;
// 					if (!count_seats.isBlank() && !count_p.isBlank()) {
// 						c = Integer.parseInt(count_seats) / Integer.parseInt(count_p);
// 						if (c == 0)
// 							c = 1000;
// 					}
//
// 					Place newPlace = Place.builder()
// 						.name(place)
// 						.address(address)
// 						.countSeats(c)
// 						.build();
//
// 					getPlace = placeRepository.save(newPlace);
//
// 					int a = c / 100;
// 					if (c % 100 == 0) {
// 						for (int z = 1; z <= a; z++) {
// 							Zone zone = Zone.builder()
// 								.name(String.valueOf((char)('A' + (z - 1))))
// 								.seatNumber(100)
// 								.build();
//
// 							zone.addPlace(newPlace);
// 							zoneRepository.save(zone);
// 						}
// 					} else {
// 						for (int z = 1; z < a; z++) {
// 							Zone zone = Zone.builder()
// 								.name(String.valueOf((char)('A' + (z - 1))))
// 								.seatNumber(100)
// 								.build();
// 							zone.addPlace(newPlace);
// 							zoneRepository.save(zone);
// 						}
// 						Zone zone = Zone.builder()
// 							.name(String.valueOf((char)('A' + (a - 1))))
// 							.seatNumber(100 + (c % 100))
// 							.build();
// 						zone.addPlace(newPlace);
// 						zoneRepository.save(zone);
// 					}
// 				}
//
// 				Optional<GoodsInfo> byNameInfo = goodsInfoRepository.findByName(name);
// 				GoodsInfo getInfo = byNameInfo.orElse(null);
// 				if (byNameInfo.isEmpty()) {
// 					Integer result = 0;
// 					Integer ageGrade = extractNumbers(age);
// 					if (ageGrade == null || ageGrade < 7) {
// 						result = 0;
// 					} else {
// 						int min = ageGrade;
// 						if (Math.abs(7 - ageGrade) < min) {
// 							min = Math.abs(7 - ageGrade);
// 							result = 7;
// 						}
// 						if (Math.abs(12 - ageGrade) < min) {
// 							min = Math.abs(12 - ageGrade);
// 							result = 12;
// 						}
// 						if (Math.abs(15 - ageGrade) < min) {
// 							min = Math.abs(15 - ageGrade);
// 							result = 15;
// 						}
// 						if (Math.abs(19 - ageGrade) < min) {
// 							min = Math.abs(19 - ageGrade);
// 							result = 19;
// 						}
// 					}
//
// 					Integer r = getRunningTime(running_time);
// 					if (r == null)
// 						break;
//
// 					GoodsInfo info = GoodsInfo.builder()
// 						.name(name)
// 						.ageGrade(result)
// 						.description(name)
// 						.runningTime(r)
// 						.build();
//
// 					Optional<GoodsCategory> findCate = goodsCategoryRepository.findByName(category);
// 					if (findCate.isEmpty()) {
// 						GoodsCategory goodsCategory = GoodsCategory.builder()
// 							.name(category)
// 							.build();
//
// 						goodsCategoryRepository.save(goodsCategory);
// 						info.updateGoodsCategory(goodsCategory);
// 					} else {
// 						info.updateGoodsCategory(findCate.get());
// 					}
// 					getInfo = goodsInfoRepository.save(info);
// 				}
//
// 				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
// 				LocalDate startDate = LocalDate.from(LocalDate.parse(start, formatter));
// 				LocalDate endDate = LocalDate.from(LocalDate.parse(end, formatter));
//
// 				if (goodsRepository.existsByTitle(name + " - 서울"))
// 					break;
//
// 				Goods goods = Goods.builder()
// 					.title(name + " - 서울")
// 					.startDate(startDate)
// 					.endDate(endDate)
// 					.goodsInfo(getInfo)
// 					.place(getPlace)
// 					.build();
//
// 				goodsRepository.save(goods);
//
// 				String[] splitTime = time.split(", ");
// 				System.out.println(splitTime[0]);
// 				String timestr = extractTextInParentheses(splitTime[0]);
// 				splitTime = timestr.split(",");
//
// 				scheduleService.createSchedule(goods, parseTimeString(splitTime[0]));
//
// 				List<MultipartFile> multipartFiles = convertUrlsToMultipartFiles(images);
// 				goodsService.createGoodsImage(multipartFiles, getInfo);
// 			}
// 		}
// 	}
//
// 	private Integer getRunningTime(String runningTime) {
// 		String[] str = runningTime.split(" ");
//
// 		Integer result = 0;
// 		if (str.length == 2) {
// 			Integer h = extractNumbers(str[0]);
// 			result = h * 60;
// 			result += extractNumbers(str[1]);
// 		} else {
// 			Integer t = extractNumbers(str[0]);
// 			if (str[0].contains("시")) {
// 				result = t * 60;
// 			} else {
// 				result = t;
// 			}
// 		}
// 		return result;
// 	}
//
// 	// 사용자 정의 MultipartFile 클래스
// 	private static class MyMultipartFile implements MultipartFile {
// 		private final byte[] content;
// 		private final String fileName;
//
// 		public MyMultipartFile(byte[] content, String fileName) {
// 			this.content = content;
// 			this.fileName = fileName;
// 		}
//
// 		@Override
// 		public String getName() {
// 			return null;
// 		}
//
// 		@Override
// 		public String getOriginalFilename() {
// 			return fileName;
// 		}
//
// 		@Override
// 		public String getContentType() {
// 			// 이미지의 Content Type에 따라서 적절한 값을 반환하도록 구현
// 			return null;
// 		}
//
// 		@Override
// 		public boolean isEmpty() {
// 			return content.length == 0;
// 		}
//
// 		@Override
// 		public long getSize() {
// 			return content.length;
// 		}
//
// 		@Override
// 		public byte[] getBytes() throws IOException {
// 			return content;
// 		}
//
// 		@Override
// 		public InputStream getInputStream() throws IOException {
// 			return null;
// 		}
//
// 		@Override
// 		public Resource getResource() {
// 			return MultipartFile.super.getResource();
// 		}
//
// 		@Override
// 		public void transferTo(File dest) throws IOException, IllegalStateException {
// 		}
//
// 		@Override
// 		public void transferTo(java.nio.file.Path dest) throws IOException, IllegalStateException {
// 			Files.write(dest, content);
// 		}
// 	}
// }
