<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<spring:eval expression="@environment.getProperty('kakao.oauth.url')" var="kakaoUrl" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=3.0">

  <title>Login</title>
  <link href="../resources/css/login.css" rel="stylesheet" type="text/css">
</head>

<body>
  <article class="login">
    <div>
      <h2>로그인</h2>

      <section>
        <a>회원</a>
      </section>
		
      <section>
        <div>
          <img src="https://cdn.icon-icons.com/icons2/1369/PNG/512/-perm-identity_89985.png" alt="">
          <input id = "userId" type="text" placeholder="아이디를 입력하세요">
        </div>

        <div>
          <img src="https://www.dsitechnologies.co.in/images/login.png" alt="">
          <input id = "userPwd" type="password" placeholder="비밀번호를 입력하세요">
        </div>
      </section>

      <button onclick="javascript:submit()">로그인</button>
      <section>
        <div>
          <a href="${pageContext.request.contextPath}/motion/signUp"> 회 원 가 입</a>
        </div>
      </section>
  
  
      <section>
        <div>
          <a href="#" id="naverIdLogin"><img src="https://image.rocketpunch.com/company/5466/naver_logo.png?s=400x400&t=inside" alt="">
            <h4>네이버 아이디로 로그인</h4>
          </a>
        </div>

        <div>
          <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=16545f631df70673eea3b36abfca50a1&redirect_uri=http://localhost:8080/tikitaka/user/kakao/callback"><img src="https://i.pinimg.com/originals/70/e8/ed/70e8edb8f187700949ab96830a3af4af.png" alt="">
            <h4>카카오톡 아이디로 로그인</h4>
          </a>
        </div>

        <div>
          <a href="#"><img src="https://w7.pngwing.com/pngs/506/509/png-transparent-google-company-text-logo.png" alt="">
            <h4>Google 아이디로 로그인</h4>
          </a>
        </div>
      </section>
    </div>
  </article>
</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/login.js" type="text/javascript"></script>
  	<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
  	<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
</html>
  