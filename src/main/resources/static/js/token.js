// JWT 디코딩을 위한 함수 (간단한 예시)
function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    } catch (e) {
        return null;
    }
}

function isLogined() {
    let token = Cookies.get('Authorization');
    if (!token) {
        return false;
    }
    return true;
}

function getUserId() {
    let token = Cookies.get('Authorization');
    const decodedToken = parseJwt(token);
    if (decodedToken) {
        return decodedToken.identify;
    }
}

// 토큰 만료 여부를 체크하는 함수
function isTokenExpiring(token) {
    const decodedToken = parseJwt(token);
    if (decodedToken && decodedToken.exp) {
        // 현재 시간이 만료 일자보다 크거나 같으면 토큰이 만료된 것으로 판단
        return Date.now() >= decodedToken.exp * 1000;
    }
    return false;
}

let isRefreshingToken = false;

function getIsRefreshingToken() {
    return isRefreshingToken;
}

function setIsRefreshingToken(value) {
    isRefreshingToken = value;
}

function refreshToken() {
    // 갱신 중인 경우 다시 요청하지 않도록 체크
    if (isRefreshingToken) {
        return Promise.resolve();
    }

    // 갱신 중 플래그를 true로 설정
    isRefreshingToken = true;
    const token = localStorage.getItem('RefreshToken');
    return fetch(`${getUrl()}/api/v1/auth/reissue`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'RefreshToken': token
        }
    })
        .then(response => {
            if (response.status !== 201) {
                throw new Error('Token refresh failed');
            }
            // 서버 응답에서 헤더에서 토큰 추출
            const newTokenFromHeader = response.headers.get('Authorization');

            // 헤더에 토큰이 없으면 에러 처리
            if (!newTokenFromHeader) {
                throw new Error('Token not found in response');
            }

            // 토큰이 헤더에 있는지 확인하여 반환
            return newTokenFromHeader;
        })
        .then(newToken => {
            // 토큰 갱신이 완료된 후 갱신 중 플래그를 false로 설정
            isRefreshingToken = false;

            // 갱신된 토큰을 반환
            return newToken;
        })
        .catch(error => {
            // 갱신 중 에러 발생 시 갱신 중 플래그를 false로 설정 후 에러 전파
            isRefreshingToken = false;
            console.log(error);
            throw error;
        });
}

function reissueToken(callback) {
    let token = Cookies.get('Authorization');
    let deferred = $.Deferred();

    if (isTokenExpiring(token)) {
        // 토큰 재발급 중이 아닌 경우에만 재발급 시도
        if (!getIsRefreshingToken()) {
            // 토큰 갱신 시도
            refreshToken().then((data) => {
                // 토큰 갱신이 완료된 후 사용자 상태 업데이트
                setTokenInCookie(data);
                /* 원래 요청 로직 에서 토큰 값으로 data를 넣어서*/
                callback(data);
            }).catch((error) => {
                // 토큰 갱신 실패 시 로그아웃 처리
                console.error('Token refresh failed:', error);
                requestLogout();
                // Cookies.remove('Authorization', {path: '/'})
            }).finally(() => {
                setIsRefreshingToken(false); // 토큰 갱신 완료 후 상태 업데이트
                deferred.resolve();
            });
        } else {
            deferred.resolve();
        }
    } else {
        /* 원래 요청 로직 */
        callback(token);
        deferred.resolve();
    }
    return deferred.promise();
}