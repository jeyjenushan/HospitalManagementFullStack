import React from "react";
import Header from "../components/Header";
import SpeicalityMenu from "../components/SpeicalityMenu";
import TopDoctors from "../components/TopDoctors";

const Home = () => {
  return (
    <div>
      <Header />
      <SpeicalityMenu />
      <TopDoctors />
    </div>
  );
};

export default Home;
