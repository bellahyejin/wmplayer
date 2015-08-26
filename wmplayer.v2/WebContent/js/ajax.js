var xhr=null; //XMLHttpRequest (비동기 서버통신)

function getXMLHttpRequest(){
   if(window.ActiveXObject){//브라우저에서 ActiveXObject를 지원한다면(IE라면)
     try{
	   return new ActiveXObject("Msxml2.XMLHTTP");
     }catch(e){
       return new ActiveXObject("Microsoft.XMLHTTP");
     }
   }else if(window.XMLHttpRequest){//비 IE라면
      return new XMLHttpRequest();
   }else{//XMLHttpRequest를 지원하지 않는다면
	  return null;
   }
}//getXMLHttpRequest

/*
xhr.open('HTTP요청방식','요청URL',비동기);
//예1) xhr.open('GET','getName.jsp',true)
//예1_2) xhr.open('GET','getName.jsp?name=gildong&age=13',true)
//예2) xhr.open('POST','getName.jsp',true)

xhr.send('파라미터');
//예1) xhr.send(null);
//예2)
    xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    xhr.send('name=gildong&age=13');
*/

function sendRequest(url, params, callback, method){//사용자 정의 메소드(open,send메소드 호출)
   xhr = getXMLHttpRequest();
   var httpMethod = method ? method : 'GET';
      //삼항 연산자 :  (조건식) ? A : B
      //예) sendRequest('getName.jsp',null,call);
   if(httpMethod != 'GET' && httpMethod != 'POST') httpMethod='GET';
	  //예) sendRequest('getName.jsp',null,call,'GETS');
   
   var httpParams = (params==null || params=='') ? null : params;
   
   var httpUrl = url;
   if(httpMethod=='GET' && httpParams !=null){
	  httpUrl = httpUrl + "?"+ httpParams;
   }
   
   xhr.open(httpMethod, httpUrl, true);
   xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
   xhr.onreadystatechange = callback;//요청후 응답데이터를 받아줄 함수를 정의. onreadystatechange변수는 ready
   //status가 변할 때 마다 호출 됨. 여기선onreadystatechange자체가 레퍼런스변수가 되어 자신이 호출 될 때마다 callback함수를 호출
   xhr.send(httpMethod=='POST' ? httpParams : null);
}







