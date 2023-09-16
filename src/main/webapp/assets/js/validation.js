function Checkform() {

    if (frm.amount.value == "") {
        frm.amount.focus();
        alert("금액을 입력해 주십시오.");
        return false;
    }
}
