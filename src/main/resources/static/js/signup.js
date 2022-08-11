/**
 *	signup 
 */


 const inputs = document.querySelectorAll("input");
 const signupButton = document.querySelectorAll("button")[0];
 
 inputs[2].onblur = () => {
     /*
         아이디 중복 확인
     */
 
     $.ajax({
         type: "get",
         url: "/api/v1/auth/signup/validation/username",
         data: {username : inputs[2].value},
         dataType: "json",
         success: (response) => {
 
         },
         error: (error) => {
             console.log("요청 실패");
             console.log(error);
         }
     });
 }
 
 signupButton.onclick = () => {
     let signupData = {
         name: inputs[0].value,
         email: inputs[1].value,
         username: inputs[2].value,
         password: inputs[3].value
     }
 
     $.ajax({
         type: "post",
         url: "/api/v1/auth/signup",
         contentType: "application/json",
         data: JSON.stringify(signupData),
         dataType: "json",
         success: (response) => {
 
         },
         error: (error) => {
             console.log("요청 실패");
             console.log(error);
         }
     })
 }
 