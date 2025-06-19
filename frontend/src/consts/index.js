export const COMMON_API_URL = "/api";

export const API_URLS = {
  user: `${COMMON_API_URL}/user`,
  signup: `${COMMON_API_URL}/user/signup`,
  chat: `${COMMON_API_URL}/chat`,
  mypage: `${COMMON_API_URL}/mypage`,
  bookreport: `${COMMON_API_URL}/reading-notes`,
  bookclubs: `${COMMON_API_URL}/bookclubs`,
  myOngoing: (userId) => `${COMMON_API_URL}/bookclubs/my/ongoing?userId=${userId}`,
  myCompleted: (userId) => `${COMMON_API_URL}/bookclubs/my/completed?userId=${userId}`,
  allBookclubs: `${COMMON_API_URL}/bookclubs/search?sortBy=least`,
  memo: (clubId, userId) => `${COMMON_API_URL}/memo/${clubId}/${userId}`,
  finalizeMemo: (clubId, userId) => `${COMMON_API_URL}/memo/${clubId}/${userId}/finalize`,
  bookclubDetail: (clubId, userId) => `${COMMON_API_URL}/bookclubs/${clubId}/detail?userId=${userId}`,
  joinBookclub: (clubId, userId) => `${COMMON_API_URL}/bookclubs/${clubId}/join?userId=${userId}`,
  completedDetail: (clubId) => `${COMMON_API_URL}/bookclubs/${clubId}/completed-detail`,
  getUserInfo: (userId) => `${COMMON_API_URL}/user/${userId}`,
  updateUserName: (userId) => `${COMMON_API_URL}/user/${userId}/name`,
  updateUserPassword: (userId) => `${COMMON_API_URL}/user/${userId}/password`,
};
