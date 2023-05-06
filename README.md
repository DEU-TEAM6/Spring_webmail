# 웹 메일 시스템 유지보수 프로젝트
> * SpringBoot 버전 웹 메일 시스템 유지/보수 프로젝트

## 개요
> * 본 프로젝트는 '객체지향설계' 수업에서 학습한 스프링부트 버전 JSP 웹 개발 방법을 기반으로 웹 메일 프로그램을 유지/보수하여 더 향상된 프로그램을 만드는 것을 목표로 한다.

> * 기존 소스 코드의 오류를 수정하는 **교정 유지보수**
> * 새로운 환경에 적응하여 작동하는 **적응 유지보수**
> * 새로운 기능을 추가 및 변경 하는 **완전화 유지보수**
> * 발생 가능한 오류에 대한 대응책을 미리 방지하는 **예방 유지보수**

위 유지보수를 통하여 사용자에게 이전보다 개선된 품질의 시스템을 제공하는 것을 목표로 한다.

## 팀 구성원
| 역할 | 학과 | 학번 | 이름 |
| :- | - | :-: | -: |
| 팀 장 | 컴퓨터소프트웨어공학과 | 20183215 | 정현수 |
| 팀원1 | 컴퓨터소프트웨어공학과 | 20183144 | 윤성민 |
| 팀원2 | 컴퓨터소프트웨어공학과 | 20183147 | 김무경 |
| 팀원3 | 컴퓨터소프트웨어공학과 | 20183119 | 송준호 |


## 개발 환경
> * IDE: Apache Netbeans 17
> * OS: Windows, Linux, Mac OS
> * DB: Oracle

## 명명 규칙
#### 공통
> * 숫자로 시작되지 않는다.   
> * 특수문자는 언더바만 허용한다.   
> * 예약어를 사용하지 않는다.   
> * 파스칼 표기법과 카멜 표기법을 사용한다.
#### 패키지 경로
> * __deu.cse.spring 이하 디렉토리에 배치한다. 
#### 패키지명
> * 가급적 한 단어의 __명사__ 를 사용하며 __소문자__ 로 표기한다.   
> ex) deu.cse.spring.login
#### 클래스명
> * 파스칼 표기법을 사용한다.
#### 메소드명
> * 해당 메소드가 어떤 기능을 수행하는지 명확하게 알 수 있도록 작성한다.   
> * GUI 또는 GUI와 관련된 기능을 하는 메소드는 파스칼 표기법을 사용한다.   
> * 이외에는 카멜 표기법을 사용하는 것을 권장하며, 되도록 동사+명사의 형태로 작성한다.
#### 변수명
> * 메소드의 매개변수는 카멜 표기법을 사용한다.   
> * 변수는 간결하지만, 약어 사용을 피하고 모든 의미를 충분히 담을 수 있도록 한다.   
> * 해당 클래스 또는 메소드에서 기능을 명확하게 표시하고 있어 충분히 변수의 기능에 대한 유추가 가능하지 않는 이상 한 글자로 된 이름을 사용하지 않는다.   
> * 선언 시점에서 초기화한다.   
> * 반복문에서 인덱스로 사용할 변수는 i,j,k 등으로 사용한다.
