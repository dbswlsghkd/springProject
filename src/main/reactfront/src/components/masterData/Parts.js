import React, { useState, useEffect } from 'react';
import Footer from './layouts/Footer';
import Header from './layouts/Header';
import 'bootstrap/dist/css/bootstrap.min.css';

const PartManagement = () => {
    const [parts, setParts] = useState([]);
    const [searchInput, setSearchInput] = useState('');
    const [showCreateModal, setShowCreateModal] = useState(false);
    const [showUpdateModal, setShowUpdateModal] = useState(false);
    const [selectedPart, setSelectedPart] = useState({ part_code: '', part_name: '', part_std: '' });

    useEffect(() => {
        // Fetch parts data and update state
        // Example fetchParts();
    }, []);

    const handleSearchInput = (e) => setSearchInput(e.target.value);

    const openCreateModal = () => setShowCreateModal(true);
    const closeCreateModal = () => setShowCreateModal(false);
    const openUpdateModal = (part) => {
        setSelectedPart(part);
        setShowUpdateModal(true);
    };
    const closeUpdateModal = () => setShowUpdateModal(false);

    const handleCreateSubmit = () => {
        // Implement the logic for creating a new part
        closeCreateModal();
    };

    const handleUpdateSubmit = () => {
        // Implement the logic for updating the selected part
        closeUpdateModal();
    };

    return (
        <>
            <Header>
            <div className="input-group ms-auto">
                <button className="btn btn-sm btn-outline-dark" onClick={openCreateModal}>등록</button>
            </div>
            <div className="input-group mb-3 w-25 ms-auto">
                <input
                    type="text"
                    className="form-control"
                    placeholder="품번, 품명, 규격"
                    value={searchInput}
                    onChange={handleSearchInput}
                />
                <button className="btn btn-outline-secondary" type="button">
                    <i className="bi bi-search"></i>
                </button>
            </div>

            <table className="table table-striped table-sm table-hover">
                <thead className="table-dark">
                <tr>
                    <th scope="col" style={{ width: '150px', textAlign: 'center' }}>품번</th>
                    <th scope="col" style={{ width: '150px', textAlign: 'center' }}>품명</th>
                    <th scope="col" style={{ width: '150px', textAlign: 'center' }}>규격</th>
                </tr>
                </thead>
                <tbody>
                {parts.map((part) => (
                    <tr key={part.part_code} onClick={() => openUpdateModal(part)}>
                        <th>{part.part_code}</th>
                        <td>{part.part_name}</td>
                        <td>{part.part_std}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <nav aria-label="Page navigation">
                <ul className="pagination justify-content-center">
                    <li className="page-item">
                        <a className="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    {/* Dynamic pagination buttons here */}
                    <li className="page-item">
                        <a className="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>

            {/* Create Part Modal */}
            {showCreateModal && (
                <div className="modal fade show" style={{ display: 'block' }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">품번 등록</h5>
                                <button className="btn-close" onClick={closeCreateModal}></button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="mb-3">
                                        <label className="form-label">품번</label>
                                        <input type="text" className="form-control form-control-sm" />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">품명</label>
                                        <input type="text" className="form-control form-control-sm" />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">규격</label>
                                        <input type="text" className="form-control form-control-sm" />
                                    </div>
                                    <button type="button" className="btn btn-outline-dark btn-sm" onClick={handleCreateSubmit}>
                                        등록 완료
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            {/* Update Part Modal */}
            {showUpdateModal && (
                <div className="modal fade show" style={{ display: 'block' }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">품번 수정</h5>
                                <button className="btn-close" onClick={closeUpdateModal}></button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="mb-3">
                                        <label className="form-label">품번</label>
                                        <input type="text" className="form-control form-control-sm" value={selectedPart.part_code} readOnly />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">품명</label>
                                        <input
                                            type="text"
                                            className="form-control form-control-sm"
                                            value={selectedPart.part_name}
                                            onChange={(e) => setSelectedPart({ ...selectedPart, part_name: e.target.value })}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">규격</label>
                                        <input
                                            type="text"
                                            className="form-control form-control-sm"
                                            value={selectedPart.part_std}
                                            onChange={(e) => setSelectedPart({ ...selectedPart, part_std: e.target.value })}
                                        />
                                    </div>
                                    <button type="button" className="btn btn-outline-dark btn-sm" onClick={handleUpdateSubmit}>
                                        수정 완료
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            )}
            </Header>
            <Footer />
        </>
    );
};

export default PartManagement;
