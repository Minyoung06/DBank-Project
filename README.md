# 💰 KB 이체 및 금융상품 가입 시스템

--- 

- **주제**: 은행 이체 서비스 및 상품 가입 서비스 구현
- **기간**: 2025.05.12 ~ 2025.mm.dd
- **참여 인원**: 5명 (고두환, 김은수, 양민영, 이건우, 이수현)
- **목표**: MySQL, JDBC를 활용한 은행 시스템 구현 및 데이터 흐름 학습

---

## 👥 팀원 소개 & 역할

| 이름     | 역할                                                                                                                                                                                                                                       | GitHub ID       |
|----------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| 고두환   | • 거래 VO 및 DAO 구현 (TransactionVO, TransactionDao, TransactionDaoImpl)<br>• 거래 서비스 계층 구현 (TransactionService)<br>• 거래 실행부 구현 (TransactionApp)<br>• DB 테이블 정의 (TransactionTable)                                                            | [GitHub](https://github.com/story125)   |
| 김은수   |                                                                                                                                                                                                                                          | [GitHub](https://github.com/ensookim)   |
| 양민영   | • Notion 문서화 / ReadMe 문서 작성<br>• 계좌 VO 및 DAO 구현 (AccountVO, AccountDao, AccountDaoImpl)<br>• 상품 목록 조회 서비스 구현 (ProductListService, ProductListServiceImpl)<br>• 메인, 상품 리스트 실행부 구현 (MainApp, ProductListApp)<br>• DB 테이블 정의 (AccountTable) | [GitHub](https://github.com/Minyoung06)   |
| 이건우   |                                                                                                                                                                                                                                          | [GitHub](https://github.com/Kyun17)   |
| 이수현   | • 유저 VO 및 DAO 구현 (UserVO, UserDao, UserDaoImpl)<br> • 유저 서비스 계층 구현 (UserService, UserServiceImpl)<br>• 세션 관리 (Session)<br>• 메인 실행 (MainApp)<br>• 유틸리티 클래스 (AccountUtil, ValidatorUtil)<br>• DB 테이블 정의 (UserTable)                        | [GitHub](https://github.com/soohyun1904)   |

---

## 🛠 기술 스택

### Programming & Database
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

### Build & Dependency
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

### IDE & Tools
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![MySQL Workbench](https://img.shields.io/badge/MySQL%20Workbench-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

### Version Control & Docs
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Markdown](https://img.shields.io/badge/Markdown-000000?style=for-the-badge&logo=markdown&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)

---

## 시작 가이드
> 완성되고 내용 추가 예정

---

## 🖼️ 화면 구성

> 주요 기능 실행 결과 화면을 첨부할 예정
> (예: 로그인 화면, 상품 가입 화면, 거래 내역 조회 등)

---

## 🗂️ 폴더 구조
```
DBank-Project/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── app/ # 메인 실행 클래스들 (MainApp 등)
│ │ │ ├── common/ # 공통 유틸 및 세션 객체
│ │ │ ├── dao/ # DAO 인터페이스 및 구현체
│ │ │ ├── database/ # DB 연결 유틸 (JDBCUtil)
│ │ │ ├── domain/ # VO(Value Object) 클래스들
│ │ │ ├── service/
│ │ │ │ ├── product/ # 상품 가입 서비스
│ │ │ │ ├── productList/ # 상품 목록 조회 서비스
│ │ │ │ ├── transaction/ # 거래 처리 서비스
│ │ │ │ ├── transactionList/ # 거래 내역 조회 서비스
│ │ │ │ └── user/ # 사용자 관련 서비스
│ │ │ └── util/ # 유틸리티 클래스 (AccountUtil, ValidatorUtil)
│ │ └── resources/ # 설정 파일 (application.properties)
│ └── test/
│     ├── java/
│     │   ├── app/ # 메인 앱 테스트
│     │   └── manualTest/ # DAO 수동 테스트
│     └── resources/ # 테스트용 리소스
│
├── build/ # 빌드 결과물 (컴파일된 클래스 등)
├── docs/ # 문서 및 ERD 이미지
├── gradle/ # Gradle Wrapper 설정
└── build.gradle / settings.gradle # Gradle 설정 파일
```
---

## 🗃️ 데이터베이스 구조

> 👉 [DB 구조 자세히 보기](./docs/database.md)

---

## 🔗 배포 링크

- **Notion 프로젝트 소개 페이지**: [링크 예정]
- **GitHub Pages 배포 주소**: [링크 예정]


