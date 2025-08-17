package seg.work.carog.server.async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AsyncService {

}
