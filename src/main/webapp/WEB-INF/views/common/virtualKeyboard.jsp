<%--
  Created by IntelliJ IDEA.
  User: yebin
  Date: 2023/09/17
  Time: 1:30 PM
  To change this template use File | Settings | File Templates.
--%>
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

    let keyboard = new Keyboard({
        onChange: input => onChange(input),
        onKeyPress: button => onKeyPress(button),
        layout: {
            default: ["1 2 3", "4 5 6", "7 8 9", "{bksp} 0 {enter}"]
        },
        theme: "hg-theme-default hg-layout-numeric numeric-theme"
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