const searchButton = document.querySelector(".search-button");

searchButton.onclick = () => {
	load(1);
}

let nowPage = 1;

load(nowPage)

function load(nowPage) {
	const searchFlag = document.querySelector(".search-select").value;
	const searchValue = document.querySelector(".search-input").value;
	
	$.ajax({
		async: false,
		type: "get",
		url: "/api/v1/notice/list/" + nowPage,
		data: {
			"searchFlag": searchFlag,
			"searchValue": searchValue
		},
		dataType: "json",
		success: (response) => {
			if(response.data[0] != null) {
				getList(response.data);
				getPageNumbers(response.data[0].totalNoticeCount);
			}else{
				getList(new Array());
				getPageNumbers(0);
			}
		},
		error: (error) => {
			console.log(error);
		}
		
	});
	
}

function getList(list){
	const tbody = document.querySelector("tbody");
	tbody.innerHTML = "";
	
	list.forEach(notice => {
		tbody.innerHTML += `
			<tr class="notice-row">
                <td>${notice.noticeCode}</td>
                <td>${notice.noticeTitle}</td>
                <td>${notice.userId}</td>
                <td>${notice.createDate}</td>
                <td>${notice.noticeCount}</td>
            </tr>
		`;
	});
	
	const noticeRows = document.querySelectorAll(".notice-row");
	noticeRows.forEach((row) => {
		row.onclick = () => {
			const noticeCode = row.querySelectorAll("td")[0].textContent;
			location.href = "/notice/detail/" + noticeCode;
		}
	});
	
	
}

function getPageNumbers(totalNoticeCount) {
	const pageButtons = document.querySelector(".page-buttons");
	
	const totalPageCount = totalNoticeCount % 10 == 0 ? totalNoticeCount / 10 : (totalNoticeCount / 10) + 1;
	
	const startIndex = nowPage % 5 == 0 ? nowPage - 4 : nowPage - (nowPage % 5) + 1;
	const endIndex = startIndex + 4 <= totalPageCount ? startIndex + 4 : totalPageCount;
	
	console.log(
		`
			totalPageCount: ${totalNoticeCount}
			startIndex: ${startIndex}
			endIndex: ${endIndex}
		`
		
	);
	
	pageButtons.innerHTML = ``;
	
	if(startIndex != 1) {
		pageButtons.innerHTML += `
			<button type="button" class="page-button pre">&lt;</button>
		`;
	}
	
	for(let i = startIndex; i <= endIndex; i++) {
		pageButtons.innerHTML += `
			<button type="button" class="page-button">${i}</button>
		`
	}
	
	if(endIndex != totalPageCount) {
		pageButtons.innerHTML += `
			<button type="button" class="page-button next">&gt;</button>
		`;
	}
	
	if(startIndex != 1) {
		const prePageButton = document.querySelector(".pre");
		prePageButton.onclick = () => {
			nowPage = startIndex - 1;
			load(nowPage);
		}
	}
	
	if(endIndex != totalPageCount) {
		const nextPageButton = document.querySelector(".next");
		nextPageButton.onclick = () => {
			nowPage = endIndex + 1;
			load(nowPage);
		}
	}
	
	const pageNumberButtons = document.querySelectorAll(".page-button");
	pageNumberButtons.forEach(button => {
		if(button.textContent != "<" && button.textContent != ">"){
			button.onclick = () => {
				nowPage = button.textContent;
				load(nowPage);
			}
		}
	});
	
	
}

function getWriteButton() {
	const listFooter = document.querySelector(".list-footer");
	
	if(getUser() != null) {
		if(getUser().userRoles.includes("ROLE_ADMIN")) {
			listFooter.innerHTML += `
				<button type="button" class="notice-add-button">글 쓰기</button>
			`;
			
			const noticeAddButton = document.querySelector(".notice-add-button");
			
			noticeAddButton.onclick = () => {
				location.href = "/notice/addition";
			}
		}
	}
}

getWriteButton();

































