import axios from 'axios'
export {convertDate, getCookie, capitalizeFirstLetter, RequestHelper};

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

class RequestHelper {
	/**
	* Crear un request de tipo GET a `path` y ejecuta los distintos callbacks por contexto
	* @param {String} path The name to say hi to
	* @param {Object} callback The name to say hi to
	*/
	static get(path, callback) {
		axios
			.get(path)
			.then(response => {
				var data = response.data;

				switch (data.code) {
					case 200:
						if (callback.success)
							callback.success(data);
						break;
					case 400:
						if (callback.failed)
							callback.failed(data);
						break;
					case 403:
						if (callback.notLoggedIn)
							callback.notLoggedIn(data);
						break;
					case 404:
						if (callback.empty)
							callback.empty(data);
						break;
					default:
						if (callback.default)
							callback.default(data);
				}
			})
			.catch(error => {
				if (callback.error)
					callback.error(error);
			})
			.then(() => {
				// always
				if (callback.always)
					callback.always();
			})
	}
	/**
	* Crear un request de tipo POST a `path` con `data` y ejecuta los distintos callbacks por contexto
	* @param {String} path The name to say hi to
	* @param {Object} callback The name to say hi to
	*/
	static post(path, data, callback) {
		axios
			.post(path, data)
			.then(response => {
				var data = response.data;

				switch (data.code) {
					case 200:
						if (callback.success)
							callback.success(data);
						break;
					case 400:
						if (callback.failed)
							callback.failed(data);
						break;
					case 403:
						if (callback.notLoggedIn)
							callback.notLoggedIn(data);
						break;
					case 404:
						if (callback.empty)
							callback.empty(data);
						break;
					default:
						if (callback.default)
							callback.default(data);
				}
			})
			.catch(error => {
				if (callback.error)
					callback.error(error);
			})
			.then(() => {
				// always
				if (callback.always)
					callback.always();
			})
	}
}