package org.example.onlinecourse.exception;

public class MessageType {

    public static final String COURSE_NOT_FOUND = "Verilen ID'ye sahip kurs bulunamadı.";
    public static final String STUDENT_NOT_FOUND = "Verilen ID'ye sahip öğrenci bulunamadı.";
    public static final String ENROLLMENT_NOT_FOUND = "Verilen ID'ye sahip kayıt bulunamadı.";
    public static final String INSTRUCTOR_NOT_FOUND = "Verilen ID'ye sahip eğitmen bulunamadı.";
    public static final String CATEGORY_NOT_FOUND = "Verilen ID'ye sahip kategori bulunamadı.";
    public static final String USER_NOT_FOUND = "Verilen e-posta adresine sahip kullanıcı bulunamadı.";
    public static final String ADMIN_NOT_FOUND = "Verilen ID'ye sahip admin bulunamadı.";
    public static final String INVALID_ROLE_ADMIN = "Yalnızca admin rolüne sahip kullanıcı eklenebilir.";
    public static final String INVALID_ROLE_INSTRUCTOR = "Yalnızca instructor rolüne sahip kullanıcı eklenebilir.";
    public static final String INVALID_ROLE_STUDENT = "Yalnızca student rolüne sahip kullanıcı eklenebilir.";
    public static final String NO_PERMISSION_ADMIN = "Bu kullanıcıya erişim yetkiniz yok.";
    public static final String NO_PERMISSION_CATEGORY = "Bu kategoriyi işleme yetkiniz yok";
    public static final String NO_PERMISSION_COMMENT = "Bu yorumu işleme yetkiniz yok";
    public static final String NO_PERMISSION_COURSE = "Bu kursu işleme yetkiniz yok";
    public static final String COMMENT_NOT_FOUND = "Verilen ID'ye sahip yorum bulunamadı.";
    public static final String COURSE_ALREADY_IN_FAVORITES = "Bu kurs zaten favorilere eklenmiş.";
    public static final String NO_OPERATION_PERMISSION = "İşlem yetkiniz yok.";
    public static final String REFRESH_TOKEN_NOT_FOUND = "Yenileme (refresh) token bulunamadı.";
    public static final String REFRESH_TOKEN_EXPIRED = "Yenileme (refresh) token süresi dolmuş.";
    public static final String TOKEN_INVALID = "Geçersiz veya hatalı JWT token.";
}
