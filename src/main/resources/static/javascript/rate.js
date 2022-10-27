
const baseUrl = "http://localhost:8080";
const csrfToken = document.getElementById("csrf")
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
            console.log(data)
            populateUser(data)
        })
        .catch(err => console.error(err))
}

const populateUser = (obj) =>{
    creditScore.value=obj.borrowerDto.creditScore;
    loanAmount.value=obj.borrowerDto.loanAmount;
    // const loanPurposeSelection = document.getElementById("loan-purpose");
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
    console.log("checking rate")
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
    console.log(bodyObj)
    await fetch(`${baseUrl}/rate/getrate`, {
        method: "POST",
        body: bodyObj,
        headers: headers
    })
        .catch(err => console.error(err))
        .then(response => response.json())
        .then(data =>{
            console.log(data)
            rateBody.innerHTML="";
            for (let i = 0; i < data.length; i++) {
                createRateCard(data[i]);
            }
        })




}
const createRateCard = (obj) =>{
    let n = loanTerm.value *12;
    let p = loanAmount.value;
    let r = obj.rate / 1200;
    console.log("loan amount: " + p + "loan interest: " + r + "loan term: " + n)
    let monthlyPmt =Math.round(p * r * ((r +1)**n) / ((1+r)**n -1)) ;
    let rateCard = document.createElement("tr");
    rateCard.innerHTML =`
            <td>${obj.rate}%</td>
            <td>${obj.day15}</td>
            <td>${obj.day30}</td>
            <td>${obj.day45}</td>
            <td>$ ${monthlyPmt}</td>           
        `
    rateBody.appendChild(rateCard);
}