package com.pot.app.infoarchive.testUtil.annotation;

import com.pot.app.infoarchive.testUtil.ClickHouseExtension;
import com.pot.app.infoarchive.testUtil.TestBDFacade;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
@ExtendWith(ClickHouseExtension.class)
@Import(TestBDFacade.class)
public @interface ClickHouse {
}
