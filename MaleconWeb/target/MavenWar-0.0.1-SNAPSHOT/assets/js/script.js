//전역변수

var need_commit=false;
console.log(need_commit);
//--------------------------------------------------------------------------------
(function($){
$(document).ready(function(){

$('#cssmenu li.active').removeClass('open').children('ul').hide();
	$('#cssmenu li.has-sub>a').on('click', function(){
		$(this).removeAttr('href');
		var element = $(this).parent('li');
		if (element.hasClass('open')) {
			element.removeClass('open');
			element.find('li').removeClass('open');
			element.find('ul').slideUp(200);
		}
		else {
			element.addClass('open');
			element.children('ul').slideDown(200);
			//element.siblings('li').children('ul').slideUp(200);
			//element.siblings('li').removeClass('open');
			//element.siblings('li').find('li').removeClass('open');
			//element.siblings('li').find('ul').slideUp(200);
		}
	});

});
})(jQuery);
// @-------------------------------------------------------------------------------------
    // @breif contenteditable 속성을 가진경우

    contents = document.getElementsByClassName("rowColumn");



    document.addEventListener("DOMContentLoaded", function() {



        // @breif rowColumn 클래스의 갯수 만큼 반복문을 실행한다.

        Array.from(contents).forEach(function(content) {



            // @breif 마우스로 해당영역을 더블클릭 한경우

            content.addEventListener("dblclick", function(event) {



                // @breif 전체 테이블 컬럼( td > p )에서 현재 사용중인 값의 존재여부를 확인한다.

                Array.from(contents).forEach(function(defaultVal) {



                    /*
                    // @details 빈값( null )이 존재하는지 체크한다.
                    if(
                           defaultVal.textContent == ""
                        || defaultVal.textContent == null
                        || defaultVal.textContent == undefined
                        || (defaultVal.textContent != null
                        && typeof defaultVal.textContent == "object"
                        && !Object.keys(defaultVal.textContent).length == ""))
                    {

                        // @details 내용이 존재하지 않다면 data 태그의 기본값으로 되돌린다.
                        defaultVal.textContent = defaultVal.dataset.default;
                    }
                    */

                    // @details 저장하지 않은 내용이라고 판단하여 data 태그의 기본값으로 되돌린다.
                    defaultVal.textContent = defaultVal.dataset.default;

                    // @breif 수정 불가 상태로 되돌린다.

                    defaultVal.contentEditable = false;

                    defaultVal.style.border = "0px";

                });



                if(content.isContentEditable == false) {



                    // @details 편집 가능 상태로 변경

                    content.contentEditable = true;



                    // @details 텍스트 문구 변경

                    // content.textContent = "";



                    // @details CSS 효과 추가

                    content.style.border = "1px solid #FFB6C1";



                    // @details 포커스 지정

                    content.focus();

                }

            });



            // @breif 키보드 입력이 방생한 경우 실행

            content.addEventListener("keypress", function(event) {



                // @breif Enter키 입력시 실행

                if(event.key === "Enter") {



                    // @details 입력된 값이 빈값( null )인지 체크한다.

                    if(

                           content.textContent == ""

                        || content.textContent == null

                        || content.textContent == undefined

                        || (content.textContent != null

                        && typeof content.textContent == "object"

                        && !Object.keys(content.textContent).length == ""))

                    {



                        // @details 내용이 존재하지 않다면 data 태그의 기본값으로 되돌린다.

                        content.textContent = content.dataset.default;

                    } else {



                        // @details 내용의 수정이 완료되었다면 data 태그의 기본값도 바꿔준다.

                        content.dataset.default = content.textContent;

                    }



                    // @breif 수정 불가 상태로 되돌린다.

                    content.contentEditable = false;

                    content.style.border = "0px";

                }

            });

        });

    });
//@----------------------------------------------------------------------------------
function setBg(){
 	 // del개수
  	var del_length = document.getElementsByName("del").length;
  	
   for (var i=0; i<del_length; i++) {
   			tr=document.getElementsByName("del")[i].parentNode.parentNode; //tr 선언
            if (document.getElementsByName("del")[i].checked == true) { //체크되있을 때
            	
                tr.style.backgroundColor="#FAFAFA";  	//연한 회색
                }
             else{ //체크 안되어 있을 때
             blank=document.getElementById("newArray["+(i+1)+"][1]");
                if(blank.disabled==false || tr.style.opacity=="0.1"){//  빈칸 수정 가능시
                	tr.style.backgroundColor="skyblue";  	//빨강
               }
                else{
                	tr.style.backgroundColor="white"; //화이트
               }
             }  
 
    }
 

 
 //tr.style.display=(t.checked)? "none" : "";
 }
 //@----------------------------------------------------------------------------------
function setBg2(t){
   var del_in_length = document.getElementsByName("del_in").length;
             
               for (var i=0; i<del_in_length; i++) {
   			tr2=document.getElementsByName("del_in")[i].parentNode.parentNode; //tr 선언
            if (document.getElementsByName("del_in")[i].checked == true) { //체크되있을 때
            	
                tr2.style.backgroundColor="#FAFAFA";  	//연한 회색
                }
             else{ //체크 안되어 있을 때
                	tr2.style.backgroundColor="skyblue";  	//연한 파랑
             }  
             }
 }

//@------------------------------------------------------------------------------------------
function addRow() {
	
	repeat_cnt=prompt("추가할 행의 갯수를 입력해주세요");

	if(isNaN(Number(repeat_cnt)))
	{
		alert("정수를 입력해주세요");
		addRow();
	}else{
  // table element 찾기
  const table = document.getElementById('table');
  for(var index=0;index<repeat_cnt;index++){
  // 새 행(Row) 추가
  const newRow = table.insertRow();
  
  // td element 찾기
	var tr= table.getElementsByTagName("tr");
	var th= table.getElementsByTagName("th");
   
  // row,collumn 개수
  var rowcnt = table.rows.length-1;
  var colcnt = parseInt(th.length);
  
	console.log(rowcnt);
  console.log(colcnt);
  
  //배열 선언 및  새 행에 cell 추가
   var arr=[];
  for (var i = 0; i <colcnt; i++) {
      arr[i]=newRow.insertCell(i);

    }
  
  
   
  // Cell에 텍스트 추가
  for (var i = 0; i <colcnt ; i++) {
  	 if(i==0){
  	  arr[i].innerHTML+="<input type='checkbox' name='del_in' value='1' onclick='setBg2()' onmouseover='mouse_on(this)' onmouseout='mouse_off(this)' onclick='mouse_click(this)'>";
  	 }
  	 else{
      arr[i].innerHTML+="<input type='text' id='newRow["+rowcnt+"]["+i+"]' value='' name='newRow["+rowcnt+"]["+i+"]' style='width:${k.length()*2}em' onmouseover='mouse_on(this)' onmouseout='mouse_off(this)' onclick='mouse_click(this)'>"
    }
   }

   var element = document.getElementById("div_table");
	element.scrollTop = element.scrollHeight; // 스크롤 맨 아래로
	
	tr=newRow;
	
	}
	
	
	}
	need_commit=true;
	block_move();
	setBg2();
	alert(repeat_cnt+"개의 행이 추가 되었습니다.");
	
}
//@------------------------------------------------------------------------
function deleteRow() {
	
   		var obj_length = document.getElementsByName("del").length;

        for (var i=0; i<obj_length; i++) {
            if (document.getElementsByName("del")[i].checked == true) {
                tr=document.getElementsByName("del")[i].parentNode.parentNode;
                check=document.getElementsByName("del")[i];
                
                tr.style.opacity='0.1'; // 투명도
               	check.checked=false; // 체크여부 해제
               	check.disabled="disabled"; //체크 불가
               	tr.style.backgroundColor="skyblue";// 배경색
               	
               	
                //tr.style.display='none'; //안보이게
            }
        }
        

	var obj_length = document.getElementsByName("del_in").length;
	var arr=[]; //삭제 한 배열들

        for (var i=0; i<obj_length; i++) {
            if (document.getElementsByName("del_in")[i].checked == true) {
                tr=document.getElementsByName("del_in")[i].parentNode.parentNode;
				arr[i]=tr;
                //tr.remove();
            }
        }
		
		var arr_length=arr.length;
		for( var i=0;i<arr_length;i++){
			arr[i].remove();
		}
		need_commit=true;
		block_move();
    }
//@-----------------------------------------------------------------------
function commit() {
	 // table element 찾기
	  const table = document.getElementById('table');
	  
	   // row,collumn 개수
  		var rowcnt = table.rows.length;
  		
  		div =  document.getElementById('div_insert');

	  
		 div.innerHTML+="<input type='hidden' name='rowcnt' value='"+rowcnt+"' >"; //추가된 row를 포함한 row개수 보내기
	
	
	var obj_length = document.getElementsByName("del").length;
	        for (var i=0; i<obj_length; i++) {
            if (document.getElementsByName("del")[i].parentNode.parentNode.style.opacity == "0.1") {
           	document.getElementsByName("del")[i].disabled=false; //체크 불가 해제
               document.getElementsByName("del")[i].checked=true;
                
            }
        }
        
        for (var i=0; i<obj_length; i++) {
            if (document.getElementsByName("del")[i].parentNode.parentNode.style.opacity != "0.1" && document.getElementsByName("del")[i].checked == true) {
               document.getElementsByName("del")[i].checked=false;
            }
        }

	
}
//@-----------------------------------------------------------------------
function updateRow(){
	// table element 찾기
  	const table = document.getElementById('table');
  

  	// td element 찾기
  	 var td = table.getElementsByTagName("td");
   
 	 // row,collumn 개수
  	var rowcnt = table.rows.length;
 	var colcnt = parseInt(td.length/rowcnt);
  	rowcnt= rowcnt-1;
  
   var obj_length = document.getElementsByName("del").length;
   
   for (var i=1; i<=rowcnt; i++) {
            if (document.getElementsByName("del")[i-1].checked == true) {
            	 for (var j=1; j<=colcnt; j++) {
            	 console.log("newArray["+i+"]["+j+"]");
                blank=document.getElementById("newArray["+i+"]["+j+"]");
                blank.disabled=false; 
               	blank.style.border="1px solid #D8D8D8";
                
            }
            tr=document.getElementsByName("del")[i-1].parentNode.parentNode;
           // tr.style.backgroundColor="#F5F5FB"; 파란색
 
            check=document.getElementsByName("del")[i-1];
            check.checked = false;
            //tr.style.backgroudColor=;
        }
        
    }
    setBg();
	need_commit=true;
	block_move();
    alert("선택한 행이 수정 가능해졌습니다.");
  }
  
  //@----------------------------------------------------------------------------
function mouse_on(t){
	tr=t.parentNode;
	tr.style.color="red";
	
}
//--------------------------------
function mouse_off(t){
	tr=t.parentNode;
	
	tr.style.color="black";
}
//------------------
function mouse_click(t){
	tr=t.parentNode;
	tds=tr.childNodes;
    check=tds[1].childNodes[1]; //체크박스
    blank=tds[5].childNodes[1]; //빈칸
    console.log(blank);
    if(blank.disabled==true) {
	    if(check.checked){
	    check.checked=false;
	    }else{
	    check.checked=true;
	    }
    }
    setBg();
}
//------------------
function setAll(t){
	checkBoxes=document.getElementsByName("del");
	if(t.checked==true)
	{
		checkBoxes.forEach((checkBox)=>{checkBox.checked=true});
	}else{
		checkBoxes.forEach((checkBox)=>{checkBox.checked=false});
	}
	setBg();
	checkBoxes=document.getElementsByName("del_in");
	if(t.checked==true)
	{
		checkBoxes.forEach((checkBox)=>{checkBox.checked=true});
	}else{
		checkBoxes.forEach((checkBox)=>{checkBox.checked=false});
	}
	setBg2();
}
//------------------------------
function goRollback(){ 

	var tr=$(".row_content"); //tr
	
	rollback_row = new Array();
	var cnt=0;

	for(var i=0; i < tr.length; i++) {
		var blank=tr[i].childNodes[5].childNodes[1]; //빈칸
		
		if(blank.disabled==false || tr[i].style.opacity=="0.1")
		{
     		   rollback_row[cnt]=$(tr[i]).attr('id'); // rollback_row에 tr 넣기
				console.log($(tr[i]).attr('id'));
				cnt++;
        }
	}
	sessionStorage.setItem("rollback_row", rollback_row ); // 저장 
//	sessionStorage.getItem("rollback_row"); 
//	sessionStorage.length; // 1 
//	sessionStorage.key(0); // mineItRecord 
//	sessionStorage.removeItem("rollback_row"); // 삭제 
//	sessionStorage.clear(); // 전체삭제


	
	alert("롤백되었습니다.");

	console.log(rollback_row);
	location.reload(); //새로고침
}
//------------------------------
function goCommit(){
	location.href="commit.jsp";
}
//----------------------------

$(document).ready(function(){ //검색창
		$("#keyword").keyup(function(){
		var k=$(this).val();
		$("li").hide();
		var temp=$("li:contains("+k+")");		
		
		temp.find("ul").find("li").show();
		temp.siblings("li").hide();
		temp.show();
		
		})
	});
//----------------------------------------------
function block_move(){
	if(need_commit==true)	{	
		var button =  $(':submit');
		
		button[0].disabled=true;
		
	}
}
//--------------------------------
$(document).ready(function(){ // li에서 href전에 한번 묻는것
$('a').click(function(e) { 
		console.log($(this).attr("class"));
		if(need_commit==true && $(this).attr("class")!="b"){
			var conf=confirm("정말로 페이지를 이동하시겠습니까? Commit을 안하고 이동하시면 저장이 안 됩니다.");
			if(!conf){				
				e.preventDefault();			}
		}
	 });
});
//-------------------------------
function setCookie(name, value, exp) {	// 쿠키 세팅
		var date = new Date();
		date.setTime(date.getTime() + exp*24*60*60*1000);
		document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
	}
//--------------------------------------
function getCookie(name) { 	//쿠키 가져오기
var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
return value? value[2] : null;
}
//------------------------------------------
function deleteCookie(name) {
document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
}
//--------------------------------------------
$(document).ready(function(){ // 
	
		rollback_row=sessionStorage.getItem("rollback_row").split(","); //세션에서  배열 가지고오기
		console.log(rollback_row[0]);
		var now_row=$(".row_content");
		
		for(var i=0; i<now_row.length ;i++){
				console.log($(now_row[i]).attr('id'));
				
			for(var j=0;j<rollback_row.length;j++){	
				
				if( String(rollback_row[j])==$(now_row[i]).attr('id')){
					
					now_row[i].style.backgroundColor="skyblue";		// 만약에 배열에 있는 id와 현재 테이블의 tr id가 같으면 	 테이블 tr 색을 skyblue로 바꾼다.
				}								
			}								
			
		}
		sessionStorage.removeItem("rollback_row");// 세션 삭제	
});



