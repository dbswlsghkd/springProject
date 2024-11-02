import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import '../../styles/styles.css'; // 스타일 파일 추가

const Header = ({ children }) => {
    return (
        <>
            {/* Navigation Section */}
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="/articles"><i className="bi bi-house-fill"></i></Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav ms-auto">
                            <li className="nav-item">
                                <Link className="nav-link active" to="#">홈</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="#">소개</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="#">서비스</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="#">연락처</Link>
                            </li>
                            {/* 로그인 여부에 따라 조건부 렌더링 */}
                            {/* {loginUserid && (
                <>
                  <li className="nav-item">
                    <span className="nav-link">{loginUserid}님!</span>
                  </li>
                  <li className="nav-item">
                    <Link className="btn btn-secondary" to="/logout">로그아웃</Link>
                  </li>
                </>
              )} */}
                        </ul>
                    </div>
                </div>
            </nav>

            {/* Sidebar and Main Content Layout */}
            <div className="container-fluid">
                <div className="row gx-0">
                    {/* Sidebar Section */}
                    <aside className="custom-sidebar col-md-3 p-3">
                        <h4 className="sidebar-title">사이드바</h4>
                        <div className="accordion" id="sidebarAccordion">
                            {/* First Accordion Item */}
                            <div className="accordion-item">
                                <h2 className="accordion-header" id="headingOne">
                                    <button className="accordion-button collapsed active" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                                        <i className="bi bi-list sidebar-icon"></i> 기준관리
                                    </button>
                                </h2>
                                <div id="collapseOne" className="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#sidebarAccordion">
                                    <div className="accordion-body">
                                        <ul className="nav flex-column">
                                            <li className="nav-item">
                                                <Link className="nav-link" to="/parts"><i className="bi bi-arrow-right-short"></i>품번관리</Link>
                                            </li>
                                            <li className="nav-item">
                                                <Link className="nav-link" to="/partners"><i className="bi bi-arrow-right-short"></i>거래업체 관리</Link>
                                            </li>
                                            <li className="nav-item">
                                                <Link className="nav-link" to="/users"><i className="bi bi-arrow-right-short"></i>사용자 관리</Link>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            {/* Second Accordion Item */}
                            <div className="accordion-item">
                                <h2 className="accordion-header" id="headingTwo">
                                    <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                        <i className="bi bi-grid-3x3-gap-fill sidebar-icon"></i>
                                    </button>
                                </h2>
                                <div id="collapseTwo" className="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#sidebarAccordion">
                                    <div className="accordion-body">
                                        <ul className="nav flex-column">
                                            <li className="nav-item">
                                                <Link className="nav-link" to="#"><i className="bi bi-arrow-right-short"></i>서브 메뉴 2-1</Link>
                                            </li>
                                            <li className="nav-item">
                                                <Link className="nav-link" to="#"><i className="bi bi-arrow-right-short"></i>서브 메뉴 2-2</Link>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            {/* Third Accordion Item */}
                            <div className="accordion-item">
                                <h2 className="accordion-header" id="headingThree">
                                    <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                        <i className="bi bi-sliders sidebar-icon"></i>
                                    </button>
                                </h2>
                                <div id="collapseThree" className="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#sidebarAccordion">
                                    <div className="accordion-body">
                                        <ul className="nav flex-column">
                                            <li className="nav-item">
                                                <Link className="nav-link" to="#"><i className="bi bi-arrow-right-short"></i>서브 메뉴 3-1</Link>
                                            </li>
                                            <li className="nav-item">
                                                <Link className="nav-link" to="#"><i className="bi bi-arrow-right-short"></i>서브 메뉴 3-2</Link>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </aside>

                    {/* Main Article Section */}
                    <main className="col-md-9 p-3 main-content">
                        <article className="bg-white p-3">
                            {children}
                        </article>
                    </main>
                </div>
            </div>
        </>
    );
};

export default Header;
