/**
 * 
 */
 
 function searchAddr() {
    new daum.Postcode({
        oncomplete: function(data) { //선택시 입력값 세팅
        	document.getElementById("postNo").value = data.zonecode; //우편번호 넣기
            document.getElementById("addrKakao").value = data.address; // 주소 넣기
            document.querySelector("input[name=addrDetail]").focus(); //상세입력 포커싱
        }
    }).open();
 };

 document.getElementById("mailDomain").addEventListener('change', function() {
    var selectedOption = this.options[this.selectedIndex].value;
    if (selectedOption !== '직접입력') {
      document.getElementById('mailAddr').value = selectedOption;
    } else {
      document.getElementById('mailAddr').value = '';
    }
 });

 function idCheck() {
    var userId = $('#userId').val();
	
	if(userId == '') {
		alert('아이디를 입력하세요.')
        return;
	}
	
    $.ajax({
        type: 'GET',
        url: '../user/idCheck',
        data: { userId: userId },
        success: function(response) {
            if (response) {
                alert('사용 가능한 ID입니다.');
                $('#joinButton').prop('disabled', false);
            } else {
                alert('이미 사용 중인 ID입니다.');
                $('#joinButton').prop('disabled', true);
            }
        },
        error: function() {
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        }
    });
 };

function checkForm() {
    var userId = $('#userId').val();
    var pwd = $('#pwd').val();
    var pwdCheck = $('#pwdCheck').val();
    var userName = $('#userName').val();
    var subjectSelected = $('input[name="subject"]:checked').val();
    var emailID = $('#emailID').val();
    var mailAddr = $('#mailAddr').val();
    var postNo = $('#postNo').val();
    var addrKakao = $('#addrKakao').val();
    var addrDetail = $('#addrDetail').val();
    var birthYear = $('#birthYear').val();
    var birthMonth = $('#birthMonth').val();
    var birthDay = $('#birthDay').val();
    var birth = birthYear + "-" + birthMonth + "-" + birthDay;
    var tel1 = $('#tel1').val();
    var tel2 = $('#tel2').val();

    var email = emailID + "@" + mailAddr;
    var tel = '010-' + tel1 + "-" + tel2;
    
    if (userId == '') {
        alert('ID를 입력하세요.');
        $('#userId').focus();
        return false;
    } else if (pwd == '') {
        alert('비밀번호를 입력하세요.');
        $('#pwd').focus();
        return false;
    } else if (pwdCheck == '') {
        alert('비밀번호 확인을 입력하세요.');
        $('#pwdCheck').focus();
        return false;
    } else if (pwd != pwdCheck) {
        alert('비밀번호와 비밀번호 확인이 다릅니다.');
        $('#pwd, #pwdCheck').val(''); // 비밀번호 초기화
        $('#pwd').focus();
        return false;
    } else if (userName == '') {
        alert('이름을 입력하세요.');
        $('#userName').focus();
        return false;
    } else if (subjectSelected == '') {
        alert('성별을 선택해주세요.')
        return false;
    } else if (tel1 == '' || tel2 == '') {
        alert('전화번호를 입력하세요.');
        $('#tel1').focus();
        return false;
    } else if (birthYear == '' || birthMonth == '' || birthDay == '' ||
        birthYear == '년도' || birthMonth == '월' || birthDay == '일'
    ) {
        alert('생년월일을 입력하세요.');
        return false;
    } else if (!validatePassword(pwd)) {
        alert('비밀번호는 8~15자의 영문, 숫자, 특수문자 조합 중 최소 3가지 종류를 포함해야 합니다.');
        return;
    }

    var user = {
        userId: userId,
        pwd: pwd
    };

    var userInfo = {
        userId: userId,
        name: userName,
        gender: subjectSelected,
        email: email,
        tel: tel,
        postCode: postNo,
        addr1: addrKakao,
        addr2: addrDetail,
        birthday: birth
    };

    var userData = {
        userDTO: user,
        userInfoDTO: userInfo
    };

    joinUser(userData);
}

function joinUser(userData) {
    console.log(userData);

    $.ajax({
        type: 'POST',
        url: '../user/signUp',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(userData),
        success: function(response) {
            console.log('성공:', response);
            if(response == true) {
                alert('회원가입에 성공하셨습니다.')
                location.href='../motion/login';
            } else {
                alert('회원가입에 실패하셨습니다.')
                return;
            }
            $('#joinButton').prop('disabled', false);
        },
        error: function(error) {
            console.error('에러:', error);
        }
    });
};

function checkPasswordMatch() {
    var password = $("#pwd").val();
    var confirmPassword = $("#pwdCheck").val();

    if (password != confirmPassword) {
        $("#passwordMatchError").html("비밀번호와 비밀번호 확인이 다릅니다.");
        $("#passwordMatchError").css("color", "red");
    } else {
        $("#passwordMatchError").html("");
    }
}

$("#pwd, #pwdCheck").on("keyup", checkPasswordMatch);

function validatePassword(pwd) {
    var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return regex.test(pwd);
}