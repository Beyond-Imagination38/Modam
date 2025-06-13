export const COMMON_API_URL = "http://localhost:8080";

export const API_URLS = {
  user: `${COMMON_API_URL}/user`,
  signup: `${COMMON_API_URL}/user/signup`,
  chat: `${COMMON_API_URL}/chat`,
  mypage: `${COMMON_API_URL}/mypage`,
  bookreport: `${COMMON_API_URL}/reading-notes`,
  bookclubs: `${COMMON_API_URL}/api/bookclubs`,
  myOngoing: (userId) => `${COMMON_API_URL}/api/bookclubs/my/ongoing?userId=${userId}`,
  myCompleted: (userId) => `${COMMON_API_URL}/api/bookclubs/my/completed?userId=${userId}`,
  allBookclubs: `${COMMON_API_URL}/api/bookclubs/search?sortBy=least`,
  memo: (clubId, userId) => `${COMMON_API_URL}/api/memo/${clubId}/${userId}`,
  finalizeMemo: (clubId, userId) => `${COMMON_API_URL}/api/memo/${clubId}/${userId}/finalize`,
  bookclubDetail: (clubId, userId) =>`${COMMON_API_URL}/api/bookclubs/${clubId}/detail?userId=${userId}`,
  joinBookclub: (clubId, userId) =>`${COMMON_API_URL}/api/bookclubs/${clubId}/join?userId=${userId}`,
  completedDetail: (clubId) =>`${COMMON_API_URL}/api/bookclubs/${clubId}/completed-detail`,
  getUserInfo: (userId) => `${COMMON_API_URL}/user/${userId}`,
  updateUserName: (userId) => `${COMMON_API_URL}/user/${userId}/name`,
  updateUserPassword: (userId) => `${COMMON_API_URL}/user/${userId}/password`,
};