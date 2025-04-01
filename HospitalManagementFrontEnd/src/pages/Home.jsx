import React from "react";
import Header from "../components/Header";
import SpeicalityMenu from "../components/SpeicalityMenu";
import TopDoctors from "../components/TopDoctors";
import Banner from "../components/Banner";

/*
Home page finished
first pages divide
components can be divide
1:Navbar.jsx
2:Header.js
3:Specializtion.jsx
4:TopDoctors.jsx
5:Banner.jsx


*/
const Home = () => {
  return (
    <div>
      <Header />
      <SpeicalityMenu />
      <TopDoctors />
      <Banner />
    </div>
  );
};

export default Home;
