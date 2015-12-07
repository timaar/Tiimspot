'use strict';

describe('Event Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEvent, MockAdres, MockPersoonEvent, MockComment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEvent = jasmine.createSpy('MockEvent');
        MockAdres = jasmine.createSpy('MockAdres');
        MockPersoonEvent = jasmine.createSpy('MockPersoonEvent');
        MockComment = jasmine.createSpy('MockComment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Event': MockEvent,
            'Adres': MockAdres,
            'PersoonEvent': MockPersoonEvent,
            'Comment': MockComment
        };
        createController = function() {
            $injector.get('$controller')("EventDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'tiimspotApp:eventUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
