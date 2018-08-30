package com.yunyou.icloudinn.bookhouse.JavaBean;

public class WriteBookData {

    private int id;
    private String name;
    private String cover;
    private String author;
    private int create_time;
    private NewChapterBean new_chapter;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public NewChapterBean getNew_chapter() {
        return new_chapter;
    }

    public void setNew_chapter(NewChapterBean new_chapter) {
        this.new_chapter = new_chapter;
    }

    public static class NewChapterBean {

        private int id;
        private int book_write_id;
        private int sequence;
        private String content;
        private int create_time;
        private int update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBook_write_id() {
            return book_write_id;
        }

        public void setBook_write_id(int book_write_id) {
            this.book_write_id = book_write_id;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }
    }
}
