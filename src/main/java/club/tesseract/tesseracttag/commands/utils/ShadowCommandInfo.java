package club.tesseract.tesseracttag.commands.utils;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShadowCommandInfo {
    String name();
    String permission() default "";
    boolean isPlayerOnly() default false;
    String permissionErr() default "You don't chat permission to run this command";
    String isPlayerOnlyErr() default "This command requires you to be a player to run";

    String description() default "";
    String usage() default "/<command>";
}
