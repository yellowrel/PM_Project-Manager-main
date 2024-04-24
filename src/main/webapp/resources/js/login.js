/**
 * 
 */

function submit() {
    var userId = $('#userId').val();
    var pwd = $('#userPwd').val();

    var userData = {
        userId: userId,
        pwd: pwd
    }

    console.log(userData);

    $.ajax({
        type: 'POST',
        url: '../user/login',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(userData),
        success: function(response) {
            console.log('성공:', response);
            if(response == true) {
                location.href = '../project/list'
            } else {
                alert('아이디, 비밀번호를 확인하세요.');
            }
        },
        error: function(error) {
        	alert('에러 발생, 관리자에게 문의하세요.');
            console.error('에러:', error);
        }
    });
};

var naverLogin = new naver.LoginWithNaverId(
		{
			clientId: "개발자센터에 등록한 ClientID",
			callbackUrl: "개발자센터에 등록한 callback Url",
			isPopup: false, /* 팝업을 통한 연동처리 여부 */
			loginButton: {color: "green", type: 3, height: 60} /* 로그인 버튼의 타입을 지정 */
		}
	);
	
/* 설정정보를 초기화하고 연동을 준비 */
naverLogin.init();