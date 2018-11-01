<script>
function XFormOnValueChange12(){

    //得到明细表的长度
    var length = $("#TABLE_DL_fd_overtime_detail tr").length-2;

    for(var i = 0; i < length;i++){

        var applierName="";
        var time="";

        //设置行项目文本

        applierName = document.getElementsByName("extendDataFormInfo.value(fd_overtime_detail."+i+".fd_name.name)")[0].value;//获取申请人姓名
        time = document.getElementsByName("extendDataFormInfo.value(fd_overtime_detail."+i+".fd_alltime)")[0].value; //获取时间
        alert(applierName );
        alert(time );

        document.getElementsByName("extendDataFormInfo.value(fd_overtime_detail."+i+".fd_beizhu)")[0].value=applierName+"本月共加班"+time+"天，请领导主意不要超过4天";
    }

}

AttachXFormValueChangeEventById("fd_name",XFormOnValueChange12);

</script>

