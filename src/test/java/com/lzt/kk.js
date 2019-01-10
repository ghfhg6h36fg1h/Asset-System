
    /*************8
     **此段代码必须放到明细表的最后一行的任意列内，不能放到明细表外。
     **请设置明细表的初始行数为1
     **请确保明细表的列数和导入的excel模板的列数一致，位置一致。
     ***************/
    function readExcel(filePath) {
        if(filePath == null || filePath == ""){
            alert("请选择文件！");
            return ;
        }
        try{
            var tb = DocListFunc_GetParentByTagName("TABLE");

            var num = 3;//从第2行开始获取数据-此处可以修改（一般excel中第1行是标题）
            var excelApp;
            var excelWorkBook;
            var excelSheet;
            excelApp = new ActiveXObject("Excel.Application");
            excelWorkBook = excelApp.Workbooks.open(filePath);
            //excelWorkBook.Worksheets.count;//得到sheet的个数
            excelSheet = excelWorkBook.ActiveSheet; //WorkSheets("sheet1")
            var length = excelSheet.usedrange.rows.count;//使用的行数
            var fieldIds = getFieldIds(tb);//获取明细字段Id
            var rowLength = tb.rows.length;

            var tableId = tb.id;//明细tableId（格式是"TABLE_DL_fd_2fd7c461bb27d8"）
            //alert(tableId);
            //alert('总共'+length+"行，从第"+(num)+"行开始导入");
            //导入前先删除原有数据，以防重复导入。（如果是累加导入，此行可屏蔽）
            //clearTableData(tableId);
            //添加导入信息
            for(var i=num;i<=length;i++){
                var values = {};
                for(var j=0;j<=fieldIds.length;j++){
                    if(j==7||j ==8||j==9||j==10){//第7列字段是日期需要转换（j=(n-1)n是日期列）-此处可能需要修改
                        values[fieldIds[j]]= getDateValue(excelSheet.Cells(i,(j+1)).value);
                    }else{
                        values[fieldIds[j]]= getValue(excelSheet.Cells(i,(j+1)).value);
                    }
                }
                DocList_AddRow(tb,null,values);
            }
            //清除第一行和最后一行，因明细表默认有1行，导入后需要清除第1行和最后一行。（如果是累加导入，此行可屏蔽）
            clearTableHeadFoot(tableId);

            excelSheet=null;
            excelWorkBook.close();
            excelApp.Application.Quit();
            excelApp=null;

        }catch(e){
            if(excelSheet !=null || excelSheet!=undefined){
                excelSheet =null;
            }
            if(excelWorkBook != null || excelWorkBook!=undefined){
                excelWorkBook.close();
            }
            if(excelApp != null || excelApp!=undefined){
                excelApp.Application.Quit();
                excelApp=null;
            }
            alert(e.message);
        }

    }
/***************************************以下代码请保持不要修改**************************************************/
function getValue(value){
    if (typeof(value) == "undefined")
    {
        value ="";
    }
    return value;
}
function getDateValue(value){
    if (typeof(value) == "undefined")
    {
        value ="";
    }else{
        var date = new Date(value);
        value = date.Format("yyyy-MM-dd");
    }

    return value;
}
function clearTableData(tableId){
    var tb = document.getElementById(tableId);
    var col_size = tb.rows.length;
    //删除现有行
    for(var k=col_size-1;k>-1;k--){
        var _row = tb.rows[k];
        if(_row.type && ('titleRow'==_row.type || 'statisticRow'==_row.type || ('templateRow'==_row.type && _row.KMSS_IsReferRow))){
            //忽略非数据行
            continue;
        }
        DocList_DeleteRow_ClearLast(_row);
        //DocList_DeleteRow(_row);
    }
}
function clearTableHeadFoot(tableId){
    var tb = document.getElementById(tableId);
    var col_size = tb.rows.length;
    //删除第一行
    for(var i=1;i<col_size;i++){
        var _row = tb.rows[i];
        if(_row.type && ('titleRow'==_row.type || 'statisticRow'==_row.type || ('templateRow'==_row.type && _row.KMSS_IsReferRow))){
            //忽略非数据行
            continue;
        }
        DocList_DeleteRow_ClearLast(_row);
        //DocList_DeleteRow(_row);
        break;
    }
    /*	col_size = tb.rows.length;
		//删除现有行
		for(var k=col_size-1;k>-1;k--){
			var _row = tb.rows[k];
			if(_row.type && ('titleRow'==_row.type || 'statisticRow'==_row.type || ('templateRow'==_row.type && _row.KMSS_IsReferRow))){
				//忽略非数据行
				continue;
			}
			DocList_DeleteRow_ClearLast(_row);
			return;
		}*/
}
//获取明细表字段ID
function getFieldIds(tb){
    var fieldIds = new Array();
    if(tb.rows.length>1){
        var row1 = tb.rows[1];
        for(var i=1;i<row1.cells.length;i++){
            var fields = row1.cells[i].getElementsByTagName("INPUT");
            if(fields.length==0){
                fields = row1.cells[i].getElementsByTagName("SELECT");
                if(fields.length==0){
                    fields = row1.cells[i].getElementsByTagName("TEXTAREA");
                }
            }
            if(fields.length>0){
                var fieldName = fields[0].name;
                //if(fieldName.indexOf('.0.fdId')==-1){
                fieldName = fieldName.replace('.0.','.!{index}.');
                fieldIds.push(fieldName);
                //}
            }
        }
    }
    return fieldIds;

}

Date.prototype.Format = function(fmt)
{
    //author: meizz
    var o =
        {
            "M+" : this.getMonth() + 1, //月份
            "d+" : this.getDate(), //日
            "h+" : this.getHours(), //小时
            "m+" : this.getMinutes(), //分
            "s+" : this.getSeconds(), //秒
            "q+" : Math.floor((this.getMonth() + 3) / 3), //季度
            "S" : this.getMilliseconds() //毫秒
        };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
