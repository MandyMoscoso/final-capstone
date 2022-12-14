
const baseUrl = "http://localhost:8080";
const csrfToken = document.getElementById("csrf")
const logOutBtn = document.getElementById("log-out")
const alertPlaceholder = document.getElementById('liveAlertPlaceholder')
const creditScore = document.getElementById("credit-score");
const loanAmount = document.getElementById("loan-amount");
const loanPurpose = document.getElementById("loan-purpose");
const loanTerm = document.getElementById("loan-term");
const loanType = document.getElementById("loan-type");
const occupancy = document.getElementById("occupancy");
const propertyType = document.getElementById("property-type");
const propertyValue = document.getElementById("property-value");
const rateBody = document.getElementById("rate-body");
const headers = {
    'Content-Type': 'application/json',
    "X-CSRF-Token": csrfToken.value
}
const  getUserInfo = async() =>{
    const response = await fetch(`${baseUrl}/getuser`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            populateUser(data);
            checkRate();
        })
        .catch(err => console.error(err))
}

const populateUser = (obj) =>{
    creditScore.value=obj.borrowerDto.creditScore;
    loanAmount.value=obj.borrowerDto.loanAmount;
    (function() {
        for(let i =0; i< loanPurpose.length; i++){
            if( loanPurpose[i].value=== obj.borrowerDto.loanPurpose){
                loanPurpose[i].selected ="selected";
            }
        }
    }());
    (function() {
        for(let i =0; i< loanTerm.length; i++){
            if( loanTerm[i].value=== obj.borrowerDto.loanTerm){
                loanTerm[i].selected ="selected";
            }
        }
    }());
    (function() {
        for(let i =0; i< loanType.length; i++){
            if( loanType[i].value=== obj.borrowerDto.loanType){
                loanType[i].selected ="selected";
            }
        }
    }());
    (function() {
        for(let i =0; i< occupancy.length; i++){
            if( occupancy[i].value=== obj.borrowerDto.occupancyType){
                occupancy[i].selected ="selected";
            }
        }
    }());
    (function() {
        for(let i =0; i< propertyType.length; i++){
            if( propertyType[i].value=== obj.borrowerDto.propertyType){
                propertyType[i].selected ="selected";
            }
        }
    }());
    propertyValue.value=obj.borrowerDto.propertyValue
}
getUserInfo();

const checkRate = async () =>{
    let bodyObj = {
        creditScore: creditScore.value,
        loanAmount: loanAmount.value,
        loanPurpose: loanPurpose.value,
        loanTerm: loanTerm.value,
        loanType: loanType.value,
        occupancyType: occupancy.value,
        propertyType: propertyType.value,
        propertyValue: propertyValue.value,
    }
    bodyObj = JSON.stringify(bodyObj)
    await fetch(`${baseUrl}/rate/getrate`, {
        method: "POST",
        body: bodyObj,
        headers: headers
    })
        .catch(err => console.error(err))
        .then(response => response.json())
        .then(data =>{
            rateBody.innerHTML="";
            if(data[0]==="false"){
                alert(data[1],data[2]);
            }else{
                for (let i = 0; i < data.length; i++) {
                    createRateCard(data[i]);
                }
            }
        })
}
const createRateCard = (obj) =>{
    let n = loanTerm.value *12;
    let p = loanAmount.value;
    let r = obj.rate / 1200;
    let monthlyPmt =Math.round(p * r * ((r +1)**n) / ((1+r)**n -1)) ;
    let rateCard = document.createElement("tr");
    rateCard.classList.add("text-right")
    rateCard.innerHTML =`
            <td>${obj.rate}%</td>
            <td>${obj.day15}</td>
            <td>${obj.day30}</td>
            <td>${obj.day45}</td>
            <td>$ ${monthlyPmt}.00</td>           
        `
    rateBody.appendChild(rateCard);
}

const alert = (message, type) => {
    alertPlaceholder.innerHTML="";
    const wrapper = document.createElement('div')
    wrapper.innerHTML = [
        `<div class="alert alert-${type} alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
        '</div>'
    ].join('')

    alertPlaceholder.append(wrapper)
}


const logOut = async() =>{
    await fetch(`${baseUrl}/logout`, {
        method: "POST",
        headers: headers
    })
        .catch(err => console.error(err))
        .then(window.location.replace("login"))
}
logOutBtn.addEventListener("click", logOut)