export const fetchApi = async (url, options) => {
  try {
    const token = localStorage.getItem("token");
    const response = await fetch(url, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      ...options,
    });

    const contentType = response.headers.get("Content-Type");

    let data;
    if (contentType && contentType.includes("application/json")) {
      data = await response.json();
    } else {
      data = await response.text();
    }

    return { status: response.status, data };
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
};
