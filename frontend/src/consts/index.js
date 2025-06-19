const COMMON_API_URL = "/api";       
const EXTERNAL_API_URL = "/external";

const API_URLS = {
  bookreport: `${COMMON_API_URL}/reading-notes`,

  signup: `${EXTERNAL_API_URL}/user/signup`,
  user: `${EXTERNAL_API_URL}/user`,
  chat: `${EXTERNAL_API_URL}/chat`,
  mypage: `${EXTERNAL_API_URL}/mypage`,

  bookclubs: `${COMMON_API_URL}/bookclubs`,
  myOngoing: function (userId) {
    return `${COMMON_API_URL}/bookclubs/my/ongoing?userId=${userId}`;
  },
  myCompleted: function (userId) {
    return `${COMMON_API_URL}/bookclubs/my/completed?userId=${userId}`;
  },
  allBookclubs: `${COMMON_API_URL}/bookclubs/search?sortBy=latest`,
  memo: function (clubId, userId) {
    return `${COMMON_API_URL}/memo/${clubId}/${userId}`;
  },
  finalizeMemo: function (clubId, userId) {
    return `${COMMON_API_URL}/memo/${clubId}/${userId}/finalize`;
  },
  bookclubDetail: function (clubId, userId) {
    return `${COMMON_API_URL}/bookclubs/${clubId}/detail?userId=${userId}`;
  },
  joinBookclub: function (clubId, userId) {
    return `${COMMON_API_URL}/bookclubs/${clubId}/join?userId=${userId}`;
  },
  completedDetail: function (clubId) {
    return `${COMMON_API_URL}/bookclubs/${clubId}/completed-detail`;
  },
  getUserInfo: function (userId) {
    return `${EXTERNAL_API_URL}/user/${userId}`;
  },
  updateUserName: function (userId) {
    return `${EXTERNAL_API_URL}/user/${userId}/name`;
  },
  updateUserPassword: function (userId) {
    return `${EXTERNAL_API_URL}/user/${userId}/password`;
  }
};
