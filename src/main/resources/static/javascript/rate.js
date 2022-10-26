
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
            // populateUser(data)
        })
        .catch(err => console.error(err))
}

const populateUser = (obj) =>{
    creditScore.innerHTML=obj.borrowerDto.creditScore;
    loanAmount.innerHTML=obj.borrowerDto.loanAmount
    // loanPurpose.innerHTML=obj.borrowerDto.loanPurpose
    // loanTerm.innerHTML=obj.borrowerDto.loanTerm
    // loanType.innerHTML=obj.borrowerDto.loanType
    // occupancy.innerHTML=obj.borrowerDto.occupancyType
    // propertyType.innerHTML=obj.borrowerDto.propertyType
    propertyValue.innerHTML=obj.borrowerDto.propertyValue




    const loanPurpose = document.getElementById("loan-purpose");
    //declare a function to check the editing user role and calling it at the same time.
    //This function will check user role then select that role from selection options.
    (function() {
        for(let i =0; i< loanPurpose.length; i++){
            if( loanPurpose[i].value=== obj.borrowerDto.loanPurpose){
                loanPurpose[i].selected ="selected";
            }
        }
    }());
}
getUserInfo();

const checkRate = () =>{
    console.log("checking rate")
    getCashoutRate();
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
            console.log(data)
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
            console.log(data)
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
            console.log(data)
        })
        .catch(err => console.error(err))
}
const getCf30hbRate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/cf30hb`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            console.log(data)
        })
        .catch(err => console.error(err))
}
const getCf30Rate =async ()=>{
    const response = await fetch(`${baseUrl}/rate/cf30`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            console.log(data)
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
            console.log(data)
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
            console.log(data)
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
            console.log(data)
        })
        .catch(err => console.error(err))
}