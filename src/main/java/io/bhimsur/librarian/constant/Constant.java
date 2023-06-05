package io.bhimsur.librarian.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Prefix {
        public static final String BOOK_PREFIX = "BK";
        public static final String MEMBER_PREFIX = "ME";
        public static final String TRANSACTION_PREFIX = "TR";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Sequence {
        public static final String BOOK_SEQ = "book_seq";
        public static final String MEMBER_SEQ = "member_seq";
        public static final String TRANSACTION_SEQ = "transaction_hist_seq";
    }
}
