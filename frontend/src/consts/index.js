const BASE = "https://modam.duckdns.org";

export const API_URLS = {
  myOngoing: (userId) => `${BASE}/api/bookclubs/my/ongoing?userId=${userId}`,
  myCompleted: (userId) => `${BASE}/api/bookclubs/my/completed?userId=${userId}`,
  allBookclubs: `${BASE}/api/bookclubs/search?sortBy=latest`,
  memo: (clubId, userId) => `${BASE}/api/memo/${clubId}/${userId}`,
  finalizeMemo: (clubId, userId) => `${BASE}/api/memo/${clubId}/${userId}/finalize`,
  bookclubDetail: (clubId, userId) => `${BASE}/api/bookclubs/${clubId}/detail?userId=${userId}`,
  joinBookclub: (clubId, userId) => `${BASE}/api/bookclubs/${clubId}/join?userId=${userId}`,
  completedDetail: (clubId) => `${BASE}/api/bookclubs/${clubId}/completed-detail`,
  bookreport: `${BASE}/api/reading-notes`,

  signup: `${BASE}/user/signup`,
  user: `${BASE}/user`,
  getUserInfo: (userId) => `${BASE}/user/${userId}`,
  updateUserName: (userId) => `${BASE}/user/${userId}/name`,
  updateUserPassword: (userId) => `${BASE}/user/${userId}/password`,
  chat: `${BASE}/chat`,
  mypage: `${BASE}/mypage`
};
