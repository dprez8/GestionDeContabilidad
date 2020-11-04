export {convertDate, getCookie, capitalizeFirstLetter};

function convertDate(date) {
	var dateParsed = new Date(Date.parse(date));
	return ('0' + dateParsed.getDate()).slice(-2) + '/'
		+ ('0' + (dateParsed.getMonth()+1)).slice(-2) + '/'
		+ dateParsed.getFullYear();
}

function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}

function capitalizeFirstLetter(string) {
	return string.charAt(0).toUpperCase() + string.slice(1);
}