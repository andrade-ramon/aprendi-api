package com.hades.login;

interface LogingServiceCallback {
	
	LoginInfoDTO onSuccess(LoginInfo loginInfo);
	
	void onError(LoginInfo loginInfo);

}
