package com.bap.service;

import com.bap.dto.EmailDetails;

public interface EmailService {

	void sendEmailAlert(EmailDetails emailDetails);
}
