<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-keyboard@latest/build/css/index.css">
    <title>Virtual Keypad</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="../../../assets/vendor/fonts/boxicons.css"/>

    <!-- Core CSS -->
    <link rel="stylesheet" href="../../../assets/vendor/css/core.css" class="template-customizer-core-css"/>
    <link rel="stylesheet" href="../../../assets/vendor/css/theme-default.css" class="template-customizer-theme-css"/>
    <link rel="stylesheet" href="../../../assets/css/demo.css"/>

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="../../../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css"/>

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="../../../assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../../assets/js/config.js"></script>
</head>

<body>
<%--<input class="input" placeholder="Tap on the virtual keyboard to start" />--%>
<div class="card" style="width: 18rem;">
    <div class="card-body">
        <div class="mb-3">
            <label for="exampleFormControlReadOnlyInputPlain1" class="form-label">Read plain</label>
            <input type="text" readonly class="input form-control-plaintext" id="exampleFormControlReadOnlyInputPlain1"
                   value="결제 비밀번호"/>
        </div>
        <div class="simple-keyboard"></div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/simple-keyboard@latest/build/index.js"></script>
<script src="src/index.js"></script>
<script>
    let Keyboard = window.SimpleKeyboard.default;

    // 숫자 0부터 9까지의 배열 생성
    let numbers = [...Array(10).keys()];

    // 숫자를 랜덤하게 섞기
    for (let i = numbers.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [numbers[i], numbers[j]] = [numbers[j], numbers[i]];
    }

    // 키보드 레이아웃 생성
    let layout = [
        numbers[0].toString() + " " + numbers[3].toString() + " " + numbers[6].toString(),
        numbers[1].toString() + " " + numbers[4].toString() + " " + numbers[7].toString(),
        numbers[2].toString() + " " + numbers[5].toString() + " " + numbers[8].toString(),
        "{bksp} " + numbers[9].toString() + " {enter}"
    ];

    let keyboard = new Keyboard({
        onChange: input => onChange(input),
        onKeyPress: button => onKeyPress(button),
        layout: {
            default: layout
        },
        theme: "hg-theme-default hg-layout-numeric numeric-theme",
        display: {
            '{bksp}': '지우기',
            '{enter}': '확인'
        },
        maxLength: 6
    });

    /**
     * Update simple-keyboard when input is changed directly
     */
    document.querySelector(".input").addEventListener("input", event => {
        keyboard.setInput(event.target.value);
    });

    console.log(keyboard);

    function onChange(input) {
        document.querySelector(".input").value = input;
        console.log("Input changed", input);
    }

    function onKeyPress(button) {
        console.log("Button pressed", button);

        /**
         * If you want to handle the shift and caps lock buttons
         */
        if (button === "{shift}" || button === "{lock}") handleShift();
    }

    function handleShift() {
        let currentLayout = keyboard.options.layoutName;
        let shiftToggle = currentLayout === "default" ? "shift" : "default";

        keyboard.setOptions({
            layoutName: shiftToggle
        });
    }

</script>
</body>
</html>