#include <iostream>
#include <assert.h>
#include <stdlib.h>
#include<list>
#include<iostream>

struct Point {
    int x;
    int y;
};

struct Shape {
public:
    virtual void draw() = 0;
    virtual void move(int x, int y) = 0;
};

struct Circle : public Shape {
private:
    double radius_;
    Point center_;

public:
    virtual void draw() override { std::cerr << "in Circle::draw()\n"; }
    virtual void move(int x, int y) override {
        std::cerr << "in Circle::move()\n";
        center_.x += x;
        center_.y += y;
    }
};

struct Square : public Shape {
private:
    double side;
    Point center_;
public:
    virtual void draw() override { std::cerr << "in Square::draw()\n"; }
    virtual void move(int x, int y) override {
        std::cerr << "in Square::move()\n";
        center_.x += x;
        center_.y += y;
    }
};

struct Rhomb : public Shape{
private:
	double side;
	double angle;
	Point center_;

public:
    virtual void draw() override { std::cerr << "in Rhomb::draw()\n"; }
    virtual void move(int x, int y) override {
        std::cerr << "in Rhomb::move()\n";
        center_.x += x;
        center_.y += y;
    }

};

void drawShapes(const std::list<Shape*>& shapes) {
	std::list<Shape*>::const_iterator it;
	for (it = shapes.begin(); it != shapes.end(); it++) {
		(*it)-> draw();
	}
}

void moveShapes(const std::list<Shape*>& shapes, int x, int y) {
	std::list<Shape*>::const_iterator it;
	for (it = shapes.begin(); it != shapes.end(); it++) {
		(*it)->move(x, y);
	}
}

int main()
{
    std::list<Shape*> shapes;
	shapes.push_back((Shape*)new Circle);
	shapes.push_back((Shape*)new Square);
	shapes.push_back((Shape*)new Circle);
	shapes.push_back((Shape*)new Square);
	shapes.push_back((Shape*)new Rhomb);

	drawShapes(shapes);
	moveShapes(shapes, 1, 1);

};

