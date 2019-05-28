//新增文章时上传文件
function addUploadAttachment1(formData,url){
    $.ajax({
        url:url,
        data : formData,
        // dataType : 'json',
        dataType : 'text',
        processData : false, // 告诉jQuery不要去处理发送的数据,必须加上，否则上传文件会出错
        contentType : false,// 告诉jQuery不要去设置Content-Type请求头，否则上传文件会出错
        enctype : "multipart/form-data",
        type : 'post',
        // beforeSend : function() {
        //     $('#li-upload-attachment').hide();
        //     $('#loading').show();
        // },
        beforeSend : function() {
            $('.load').show();
        },
        success : function(data) {
            if (url === "/ssh/test"){
                $(".result").text(data);
            } else {
                $(".result2").text(data);
            }

            // alert("保存成功");
        },
        error : function() {
            alert('保存出错');
            $('.load').hide();
        },
        complete:function () {
            $('.load').hide();

        }
    });
}
//新增文章时上传文件
function addUploadAttachment2(ip,userName,password,port,loginPassword,url){
    $.ajax({
        url:url,
        data : {
            ip : ip,
            userName : userName,
            passWord : password,
            port : port,
            loginPassword : loginPassword
        },
        enctype : "multipart/form-data",
        // dataType : 'json',
        dataType : 'text',
        type : 'post',
        // beforeSend : function() {
        //     $('#li-upload-attachment').hide();
        //     $('#loading').show();
        // },
        beforeSend : function() {
            $('.load').show();
        },
        success : function(data) {
            if (url === "/ssh/test"){
                $(".result").text(data);
            } else {
                $(".result2").text(data);
            }
        },
        error : function() {

            $('.load').hide();
        },
        complete:function () {
            $('.load').hide();

        }
    });
}
$(document).ready(function () {
    $("#test-btn").click(function () {
        var formData = new FormData();
        var file = $('#file')[0].files[0];
        var ip = $("input[name='ip']").val();
        console.log(ip);
        var userName = $("input[name='un']").val();
        console.log(userName);
        var password = $("input[name='password']").val();
        console.log(password);
        var port = $("input[name='port']").val();
        console.log(password);
        var loginPassword = $("input[name='login-password']").val();
        console.log(password);
        if(file !== undefined){
            formData.append("ip",ip);
            formData.append("userName",userName);
            formData.append("passWord",password);
            formData.append("port",port);
            formData.append("loginPassword",loginPassword);
            formData.append("file",file);
            addUploadAttachment1(formData,"/buildss-1.0-SNAPSHOT/ssh/test");
        }else {
            addUploadAttachment2(ip,userName,password,port,loginPassword,"/buildss-1.0-SNAPSHOT/ssh/test");
        }
    });


    $("#bushu").click(function () {
        var formData = new FormData();
        var file = $('#file')[0].files[0];
        var ip = $("input[name='ip']").val();
        console.log(ip);
        var userName = $("input[name='un']").val();
        console.log(userName);
        var password = $("input[name='password']").val();
        console.log(password);
        var port = $("input[name='port']").val();
        console.log(password);
        var loginPassword = $("input[name='login-password']").val();
        console.log(password);

        if(file !== undefined){
            formData.append("ip",ip);
            formData.append("port",port);
            formData.append("loginPassword",loginPassword);
            formData.append("userName",userName);
            formData.append("passWord",password);
            formData.append("file",file);
            addUploadAttachment1(formData,"/buildss-1.0-SNAPSHOT/ssh/submit");
        }else {

            addUploadAttachment1(ip,userName,password,port,loginPassword,"/buildss-1.0-SNAPSHOT/ssh/submit");
        }

    })
})
