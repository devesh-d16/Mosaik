import api from "@/config/api";

export const createPayment = async ({ planType, jwt }) => {
  try {
    const { data } = await api.post(`/api/payments/${planType}`);
    if (data.paymentLinkURL) {
      window.location.href = data.paymentLinkURL;
    }
  } catch (error) {
    if (error.response) {
      console.log(error.response.data);
      console.log(error.response.status);
      console.log(error.response.headers);
    } else if (error.request) {
      console.log(error.request);
    } else {
      console.log("Error : ", error.message);
    }
    console.log("Error : " + error);
  }
};
