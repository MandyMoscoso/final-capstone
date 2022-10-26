
const baseUrl = "http://localhost:8080";
const csrfToken = document.getElementById("csrf")
const address = document.getElementById("address");
const creditScore = document.getElementById("credit-score");
const loanAmount = document.getElementById("loan-amount");
const loanPurpose = document.getElementById("loan-purpose");
const loanTerm = document.getElementById("loan-term");
const loanType = document.getElementById("loan-type");
const occupancy = document.getElementById("occupancy");
const propertyType = document.getElementById("property-type");
const propertyValue = document.getElementById("property-value");
const saveUser = document.getElementById("saveBtn");


const editUser= (item) => {
    const editForm = document.createElement("form");
    const type =   document.querySelector(`#${item}`).getAttribute("type");
    editForm.innerHTML= `<input id="edit-${item}" type=${type} placeholder = "Enter new value"></input><input type="button" onclick="submitEdit('${item}')" value="Submit" id='userID'> </input>`;
    document.querySelector(`#${item}`).innerHTML=""
    document.querySelector(`#${item}`).appendChild(editForm);
    return ;
}

const headers = {
    'Content-Type': 'application/json',
    "X-CSRF-Token": csrfToken.value
}

const submitEdit = (item) =>{

    console.log(item)
    let editItem = document.getElementById(item);
    editItem.innerHTML=document.getElementById("edit-" + item).value;
};

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
    // const loanTerm = document.getElementById("loan-term");
    (function() {
        for(let i =0; i< loanTerm.length; i++){
            if( loanTerm[i].value=== obj.borrowerDto.loanTerm){
                loanTerm[i].selected ="selected";
            }
        }
    }());

    // const loanType = document.getElementById("loan-type");
    (function() {
        for(let i =0; i< loanType.length; i++){
            if( loanType[i].value=== obj.borrowerDto.loanType){
                loanType[i].selected ="selected";
            }
        }
    }());
    // const occupancy = document.getElementById("occupancy");
    (function() {
        for(let i =0; i< occupancy.length; i++){
            if( occupancy[i].value=== obj.borrowerDto.occupancyType){
                occupancy[i].selected ="selected";
            }
        }
    }());

    // const propertyType = document.getElementById("property-type");
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

const checkRate = () =>{
    console.log("checking rate")
    const ltv = loanAmount.value/propertyValue.value * 100;
    const baseRate = (function() {
        switch (loanTerm.value){
            case "15" : getCf15Rate(); break;
            case "20" : getCf20Rate(); break;
            case "30": getCf30Rate(); break;
            default: return "need to choose a correct loan term"
        }
    }());
    const ficoRate = [];
    let occupancyRate = getOccupancyRate();
    let propertyRate = getPropertyTypeRate();
    let cashOutRate=0;
    if(loanPurpose.value.includes("Cashout")){
        cashOutRate=getCashoutRate();
    }

    const show = async () => {
        await getFicoRate();
        console.log(ficoRate);
    }
    show();





}

const getCashoutRate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/cashout`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            // console.log(data)
            return data;
        })
        .catch(err => console.error(err))
}
const getCf15Rate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/cf15`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            // console.log(data)
            return data;
        })
        .catch(err => console.error(err))
}
const getCf20Rate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/cf20`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            // console.log(data)
            return data;
        })
        .catch(err => console.error(err))
}
// const getCf30hbRate =async ()=>{
//     const response = await fetch(`${baseUrl}/rate/cf30hb`, {
//         method: "GET",
//         headers: {
//             'Content-Type': 'application/json',
//         }
//     })
//         .then(response => response.json())
//         .then(data =>{
//             console.log(data)
//             return data;
//         })
//         .catch(err => console.error(err))
// }
const getCf30Rate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/cf30`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            // console.log(data)
            return data;
        })
        .catch(err => console.error(err))
}
const getFicoRate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/fico`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            // console.log(data)
            ficoRate=data
            return data;
        })
        .catch(err => console.error(err))
}
const getOccupancyRate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/occupancy`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            // console.log(data)
            return data;
        })
        .catch(err => console.error(err))
}
const getPropertyTypeRate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/propertytype`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            // console.log(data)
            return data;
        })
        .catch(err => console.error(err))
}