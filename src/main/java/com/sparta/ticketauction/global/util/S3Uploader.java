package com.sparta.ticketauction.global.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // final 멤버변수가 있으면 생성자 항목에 포함시킴
@RequiredArgsConstructor
@Component
public class S3Uploader {
	private final AmazonS3Client amazonS3Client;
	//AmazonS3

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	/**
	 * 로컬 경로에 저장
	 */
	public List<String> uploadFileToS3(List<MultipartFile> multipartFile, String filePath) {
		// MultipartFile -> File 로 변환
		List<File> uploadFileList = new ArrayList<>();
		try {
			for (MultipartFile file : multipartFile) {
				uploadFileList.add(convert(file).orElseThrow(()
					-> new IllegalArgumentException("[error]: MultipartFile -> 파일 변환 실패")));

			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		List<String> uploadImageUrl = new ArrayList<>();
		// s3로 업로드 후 로컬 파일 삭제
		for (File file : uploadFileList) {
			// S3에 저장된 파일 이름
			String fileName = filePath + "/" + UUID.randomUUID();
			uploadImageUrl.add(putS3(file, fileName));
			removeNewFile(file);
		}

		return uploadImageUrl;
	}

	/**
	 * S3로 업로드
	 * @param uploadFile : 업로드할 파일
	 * @param fileName : 업로드할 파일 이름
	 * @return 업로드 경로
	 */
	public String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
			CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	/**
	 * S3에 있는 파일 삭제
	 * 영어 파일만 삭제 가능 -> 한글 이름 파일은 안됨
	 */
	public void deleteS3(String filePath) throws RuntimeException {
		try {
			String key = filePath.substring(62); // 폴더/파일.확장자
			// String key = filePath.substring(filePath.indexOf(
			// )); // 폴더/파일.확장자
			try {
				amazonS3Client.deleteObject(bucket, key);
			} catch (AmazonServiceException e) {
				log.info(e.getErrorMessage());
			}

		} catch (RuntimeException exception) {
			log.info(exception.getMessage());
		}
		log.info("[S3Uploader] : S3에 있는 파일 삭제");
	}

	/**
	 * 로컬에 저장된 파일 지우기
	 * @param targetFile : 저장된 파일
	 */
	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("[파일 업로드] : 파일 삭제 성공");
			return;
		}
		log.info("[파일 업로드] : 파일 삭제 실패");
	}

	/**
	 * 로컬에 파일 업로드 및 변환
	 * @param file : 업로드할 파일
	 */
	private Optional<File> convert(MultipartFile file) throws IOException {
		// 로컬에서 저장할 파일 경로 : user.dir => 현재 디렉토리 기준
		String dirPath = System.getProperty("user.dir") + "/" + file.getOriginalFilename();
		File convertFile = new File(dirPath);

		if (convertFile.createNewFile()) {
			// FileOutputStream 데이터를 파일에 바이트 스트림으로 저장
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			return Optional.of(convertFile);
		}

		return Optional.empty();
	}

}
