# 이리와유!  
![image](https://user-images.githubusercontent.com/75787973/145670926-3e85a323-bdbb-4f05-be48-17c00d5ff813.png)



- 2021 - 2 산학프로젝트 과목 실비팀  
- `2021.09.15` ~ `2021.12.10`
- 사용자의 키워드 선택에 따른 대형 건물이나 아울렛의 점포를 추천해주는 앱 
- 대형건물을 방문하는 사용자들이 목적에 맞게 내부시설을 이용할 수 있게 하기 위한 안드로이드 기반의 어플리케이션

## Sample results
|<H4>splash<H4> |<H4>건물찾기<H4>|<H4>건의사항<H4>|
|:-:|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/75787973/145671153-04c1e4d7-539e-4a1f-822b-ed5140832154.png"  width="250" height="550"/>|<img src="https://user-images.githubusercontent.com/75787973/145671192-7efb965b-6ca9-4f5b-a2f5-40bd719b880b.png"  width="250" height="550"/>|<img src="https://user-images.githubusercontent.com/75787973/145671948-2716dfc4-9636-44ae-9cd8-4e9047f1c893.png"  width="260" height="550"/>|
  
|<H4>키워드 화면1<H4>|<H4>키워드 화면2<H4>|<H4>리뷰 점수별 추천<H4>|
|:-:|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/75787973/145672043-4a8c0cb0-220f-445a-8da5-35b0c75cabb3.png" width="250" height="550"/>|<img src="https://user-images.githubusercontent.com/75787973/145672056-d48d3e3f-cbb7-4579-beb9-e004a6acbe75.png" width="250" height="550"/>|<img src="https://user-images.githubusercontent.com/75787973/145672076-705de942-7c20-4f8a-b4b5-84e969feac83.png" width="260" height="550"/>|
  
  
|<H4>찜한 곳 출력<H4>|<H4>최종 출력<H4>|
|:-:|:-:|
|<img src="https://user-images.githubusercontent.com/75787973/145672349-ac35fe96-9c40-4187-8556-65abd56de04d.png" width="250" height="550"/>|<img src="https://user-images.githubusercontent.com/75787973/145672397-249cdd67-f5f7-4048-9703-455da278c7b6.png" width="250" height="550"/>|

## 핵심기능
- 방문 할 건물 선택
- 방문 목적에 따른 키워드 선택   
- 선택 키워드를 통한 건물 내부시설 추천   
- 평점 기반 건물 내부시설 추천
- 찜을 통해 마이페이지에 저장하여 볼 수 있는 기능
## 프로젝트 요구사항
<H3>공통</H3>
- 파이썬 언어를 사용하여 작성<br/>
- 안정된 버전의 라이브러리, 안드로이드 스튜디오를 사용해야 한다.
    
<H3>인터페이스</H3>
- cardview, listview 사용 : 건물 내부 편의시설 추천내역은 cardview, listview를 사용하고 상점 이미지도 함께 보여준다.<br/>
- 사용자가 건물을 선택한 후에 키워드를 선택할수 있도록 순서대로 화면이 표시되어야한다.<br/>
- 직관적이고 의미 전달이 명확한 화면을 사용자에게 제공한다.
  
<H3>제약사항</H3>
<H4>소프트웨어</H4>
- 시스템은 안드로이드 환경에서 동작, 실행할 수 있어야 한다.<br/> 
- 시스템은 건물, 점포 관련 정보를 처리할 수 있는 데이터베이스를 갖는다.<br/>  
- 개발을 위한 기본 환경으로 Android studio, Flask, Firebase, 크롤링을 위한 파이썬 라이브러리 BeautifulSoup 등을 기본으로 사용한다.

<H4>보안</H4>
- 사용자 권한에 따라 정보 접근이 차별화되어야 한다. (ex:// 관리자, 유저)<br/>
- 사용자의 선택에 따라 결과값이 차별화 되어야 한다.
  
<H4>정책/문화</H4>
- 사용자에게 보여주는 점포에 대한 설명이 정확해야 한다.<br/>
- 모든 출력은 한글을 사용하여 출력되어야 한다.<br/>
- 시스템의 시간은 한국 표준시(KST)에 따른다.
  
<H4>품질</H4>
- 시스템은 1년 365일 하루 24시간동안 서비스를 제공해야 한다.<br/>
- 시스템은 장애 발생시 3시간안에 정상상태로 복구되어야 한다.<br/>
- 시스템은 장애 북구시간 중에 장애 상황을 공지할 수 있어야 한다.
  
 
![image](https://user-images.githubusercontent.com/75787973/145669165-17eb0e61-2a94-4148-b6f4-35f7ce80b619.png)

## 의존성
1. python 3.9
2. selenium 90.0.4430.72
3. beautifulsoup4
5. openpyxl 
6. math
7. Flask 웹 프레임 워크
8. Firebase 활용

## 정보 (라이선스)
