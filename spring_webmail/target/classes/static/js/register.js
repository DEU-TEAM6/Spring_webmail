function checkPassword() {
    var pw1 = document.querySelector('#pw1').value;
    var pw2 = document.querySelector('#pw2').value;
    if (pw1 !== pw2) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }
}