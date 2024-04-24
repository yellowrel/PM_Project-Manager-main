<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="nowYear" value="2023" />
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=3.0">

  <title>SignUp</title>
  <link href="${contextPath}/resources/css/login.css" rel="stylesheet" type="text/css">
</head>

<body>

 <article class="join3">
    <div>

      <h2>회원가입</h2>

      <section>
        <a>정보입력</a>
      </section>
		
      <section>
		<div class= "id">
		 <input type="text" placeholder="ID" id="userId" name="userId" required>
		  <button type="button" onclick="idCheck()">중복확인</button>
		 </div>
        <div>
          <input type="text" id="emailID" required>
          <h5>@</h5>
          <input type="text" placeholder="입력" id="mailAddr" name="mailAddr" required></input>
          <select id="mailDomain">
            <option>직접입력</option>
            <option value="naver.com">naver.com</option>
            <option value="nate.com">nate.com</option>
            <option value="daum.net">daum.net</option>
            <option value="gmail.com">gmail.com</option>
          </select>
        </div>
        <div> </div>
      </section>

      <section>
        <input type="password" placeholder="비밀번호" id="pwd" name="pwd" required></input>
        <input type="password" placeholder="비밀번호 확인" id="pwdCheck" name="pwdCheck" required></input>
        <div id="passwordMatchError">8~15자의 영문, 숫자, 특수문자 조합</div>
      </section>

      <section>
        <input type="text" placeholder="이름" id="userName" required></input>

        <div>
          <select id="birthYear" name="birthYear" required>
            <option>년도</option>
            <c:forEach var="i" begin="1900" end="${nowYear}" step="1">
    			<option value="${nowYear - i + 1900}">${nowYear - i + 1900}</option>
  			</c:forEach>
          </select>

          <select id="birthMonth" name="birthMonth" required>
            <option>월</option>
            <c:forEach var="month" begin="1" end="12">
            	<c:if test="${month < 10}">
	            	<option>0${month}</option>
            	</c:if>
            	<c:if test="${month >= 10}">
	            	<option>${month}</option>
            	</c:if>
            </c:forEach>
          </select>

          <select id="birthDay" name="birthDay" required>
            <option>일</option>
            <c:forEach var="day" begin="1" end="31">
            	<c:if test="${day < 10}">
	            	<option>0${day}</option>
            	</c:if>
            	<c:if test="${day >= 10}">
	            	<option>${day}</option>
            	</c:if>
            </c:forEach>
          </select>

          <label><input type="radio" name="subject" value="false"> 여자</label>
          <label><input type="radio" name="subject" value="true"> 남자</label>
        </div>
      </section>

      <section>
        <div>
          <select>
            <option>010</option>
          </select>

          <input type="tel" id="tel1" name="tel1" required></input>
          <input type="tel" id="tel2" name="tel2" required></input>
        </div>
      </section>

      <div></div>

      <h4>선택입력 사항</h4>

      <section >
        <div>
          <input class="postSearch" type="search" placeholder="우편번호" id="postNo" name="postNo">
          <button type="button" class= "postButton" onclick="searchAddr()">주소검색</button>
        </div>

       
          <input  class="postSearch1" type="text" placeholder="도로명 주소" id="addrKakao">
          <input class="postSearch2" type="text" placeholder="상세 주소" name="addrDetail" id="addrDetail">
        
      </section>

      <section>
     	 <div class="ok">
        <button type="reset" onclick="javascript:location.href='login'">취소</button>
        <button type="button" onclick="checkForm()" id="joinButton">회원가입</button>
        </div>
      </section>

    </div>
  </article>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="${contextPath}/resources/js/signUp.js" type="text/javascript"></script>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</html>