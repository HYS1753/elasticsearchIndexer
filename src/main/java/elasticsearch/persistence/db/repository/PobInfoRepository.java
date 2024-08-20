package elasticsearch.persistence.db.repository;
/****************************************************************************************
 * Copyright(c) 2021-2023 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hys1753@kyobobook.co.kr        2024-06-23
 *
 ****************************************************************************************/

import elasticsearch.persistence.db.entity.PobInfo;
import elasticsearch.persistence.db.entity.PobInfoId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : hys1753@kyobobook.co.kr
 * @Project : elasticsearchIndexer
 * @FileName : PobInfoRepository
 * @Date : 2024-06-23
 * @description :
 */
public interface PobInfoRepository extends JpaRepository<PobInfo, PobInfoId> {

}
